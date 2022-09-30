package com.slack.api.methods.impl;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.SlackFilesUploadV2Exception;
import com.slack.api.methods.request.files.FilesCompleteUploadExternalRequest;
import com.slack.api.methods.request.files.FilesGetUploadURLExternalRequest;
import com.slack.api.methods.request.files.FilesUploadV2Request;
import com.slack.api.methods.response.files.FilesCompleteUploadExternalResponse;
import com.slack.api.methods.response.files.FilesGetUploadURLExternalResponse;
import com.slack.api.methods.response.files.FilesInfoResponse;
import com.slack.api.methods.response.files.FilesUploadV2Response;
import com.slack.api.util.http.SlackHttpClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.glassfish.grizzly.utils.Charsets;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class FilesUploadV2Helper implements AutoCloseable {
    private final MethodsClient client;
    private final OkHttpClient okHttpClient;
    private final SlackFilesUploadV2Exception underlyingException = new SlackFilesUploadV2Exception();

    public FilesUploadV2Helper(MethodsClient client) {
        this.client = client;
        this.okHttpClient = SlackHttpClient.buildOkHttpClient(client.getSlackHttpClient().getConfig());
    }

    public String uploadFile(FilesUploadV2Request.UploadFile uploadFile) throws IOException, SlackApiException, SlackFilesUploadV2Exception {
        byte[] fileContentBytes = readUploadFileBytes(uploadFile);
        FilesGetUploadURLExternalRequest req = buildGetUploadURLExternalRequest(uploadFile, fileContentBytes);
        FilesGetUploadURLExternalResponse uploadUrl = this.client.filesGetUploadURLExternal(req);
        underlyingException.getGetURLResponses().add(uploadUrl);
        if (!uploadUrl.isOk()) {
            throw underlyingException;
        }

        log.debug("Uploading a file (file_id: {}, size: {}, url: {})",
                uploadUrl.getFileId(),
                fileContentBytes.length,
                uploadUrl.getUploadUrl());

        Response uploadResult = okHttpClient.newCall(new Request.Builder()
                .url(uploadUrl.getUploadUrl())
                .post(RequestBody.create(fileContentBytes))
                .build()
        ).execute();

        try {
            FilesUploadV2Response.UploadResponse uploadResponse = FilesUploadV2Response.UploadResponse.builder()
                    .code(uploadResult.code())
                    .body(uploadResult.body().string())
                    .build();

            log.debug("Upload request response: (file_id: {}, status code: {}, response body: {})",
                    uploadUrl.getFileId(),
                    uploadResponse.getCode(),
                    uploadResponse.getBody()
            );

            underlyingException.getUploadResponses().put(uploadUrl.getFileId(), uploadResponse);
            if (uploadResponse.getCode() != 200) {
                throw underlyingException;
            }
            return uploadUrl.getFileId();

        } finally {
            if (uploadResult != null) {
                try {
                    uploadResult.body().close();
                    uploadResult.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public FilesUploadV2Response completeUploads(
            FilesUploadV2Request req,
            List<FilesCompleteUploadExternalRequest.FileDetails> files) throws SlackApiException, IOException, SlackFilesUploadV2Exception {
        FilesUploadV2Response result = new FilesUploadV2Response();

        FilesCompleteUploadExternalResponse response = this.client.filesCompleteUploadExternal(r -> r
                .files(files)
                .channelId(req.getChannel())
                .initialComment(req.getInitialComment())
                .threadTs(req.getThreadTs())
        );
        underlyingException.setCompleteResponse(response);
        if (!response.isOk()) {
            throw underlyingException;
        }

        result.setFiles(new ArrayList<>());
        for (FilesCompleteUploadExternalResponse.FileDetails file : response.getFiles()) {
            FilesInfoResponse fileInfo = this.client.filesInfo(r -> r.file(file.getId()));
            underlyingException.getFileInfoResponses().add(fileInfo);
            if (!fileInfo.isOk()) {
                throw underlyingException;
            }
            result.getFiles().add(fileInfo.getFile());
        }

        result.setOk(true);
        if (response.getFiles().size() == 1) {
            result.setFile(result.getFiles().get(0));
        }
        result.getGetURLResponses().addAll(underlyingException.getGetURLResponses());
        result.getUploadResponses().putAll(underlyingException.getUploadResponses());
        result.getFileInfoResponses().addAll(underlyingException.getFileInfoResponses());
        result.setCompleteResponse(response);
        return result;
    }

    // ----------------------------
    // private methods

    private static byte[] readUploadFileBytes(FilesUploadV2Request.UploadFile uploadFile) throws IOException {
        if (uploadFile.getFileData() != null) {
            return uploadFile.getFileData();
        }
        if (uploadFile.getFile() != null) {
            return Files.readAllBytes(uploadFile.getFile().toPath());
        }
        if (uploadFile.getContent() != null) {
            return uploadFile.getContent().getBytes(Charsets.UTF8_CHARSET);
        }
        throw new IllegalArgumentException("No file content found!");
    }

    private static FilesGetUploadURLExternalRequest buildGetUploadURLExternalRequest(
            FilesUploadV2Request.UploadFile uploadFile,
            byte[] fileContentBytes) {
        return FilesGetUploadURLExternalRequest.builder()
                .filename(uploadFile.getFilename())
                .length(fileContentBytes.length)
                .altTxt(uploadFile.getAltTxt())
                .snippetType(uploadFile.getSnippetType())
                .build();
    }

    @Override
    public void close() {
    }
}
