package com.slack.api.util.http.listener;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.joining;

/**
 * An HTTP response listener that prints the details of request/response mainly for debugging purposes.
 */
@Slf4j
public class DetailedLoggingListener extends HttpResponseListener {

    @Override
    public void accept(State state) {
        if (log.isDebugEnabled()) {
            Response response = state.getResponse();
            String body = state.getParsedResponseBody();
            if (!state.getConfig().isLibraryMaintainerMode()) {
                if (body.contains("\"access_token\":")) {
                    body = body.replaceFirst(
                            "\"access_token\":\"[^\"]+?\"", "\"access_token\":\"(redacted)\"");
                }
                if (body.contains("\"refresh_token\":")) {
                    body = body.replaceFirst(
                            "\"refresh_token\":\"[^\"]+?\"", "\"refresh_token\":\"(redacted)\"");
                }
            }

            Buffer requestBody = new Buffer();
            RequestBody requestBodyObj = response.request().body();
            try {
                if (requestBodyObj != null) {
                    requestBodyObj.writeTo(requestBody);
                }
            } catch (IOException e) {
                log.error("Failed to read the request body because {}", e.getMessage(), e);
            }

            String textRequestBody = null;
            try {
                String contentType = "";
                if (requestBodyObj.contentType() != null
                    && requestBodyObj.contentType().type() != null
                ) {
                    contentType = requestBodyObj.contentType().type().toLowerCase(Locale.ENGLISH);
                }
                if (contentType.equals("application/json") ||
                        contentType.equals("text/html")) {
                    textRequestBody = requestBody.buffer().readUtf8();
                    if (!state.getConfig().isLibraryMaintainerMode()) {
                        if (textRequestBody.contains("refresh_token=")) {
                            textRequestBody = textRequestBody.replaceFirst(
                                    "refresh_token=[^\\&]+", "refresh_token=(redacted)");
                        }
                    }
                } else {
                    textRequestBody = "(binary)";
                }
            } catch (Exception e) {
                log.debug("Failed to read request body because {}, error: {}", e.getMessage(), e.getClass().getCanonicalName());
            }

            Long contentLength = null;
            try {
                contentLength = requestBodyObj != null ? requestBodyObj.contentLength() : 0;
            } catch (IOException e) {
                log.error("Failed to read the content length because {}", e.getMessage(), e);
            }

            Headers originalRequestHeaders = response.request().headers();
            List<String> requestHeaders = new ArrayList<>();
            for (String headerName : originalRequestHeaders.names()) {
                if (headerName.toLowerCase(Locale.ENGLISH).equals("authorization")
                        && !state.getConfig().isLibraryMaintainerMode()) {
                    requestHeaders.add(headerName + ": (redacted)");
                } else {
                    requestHeaders.add(headerName + ": " + originalRequestHeaders.get(headerName));
                }
            }
            String requestHeaderLines = requestHeaders.stream().collect(joining("\n")) + "\n";

            log.debug("\n[Request URL]\n{} {}\n" +
                            "[Specified Request Headers]\n{}" +
                            "[Request Body]\n{}\n\n" +
                            "Content-Type: {}\n" +
                            "Content Length: {}\n" +
                            "\n" +
                            "[Response Status]\n{} {}\n" +
                            "[Response Headers]\n{}" +
                            "[Response Body]\n{}\n",
                    response.request().method(),
                    response.request().url(),
                    requestHeaderLines,
                    textRequestBody,
                    requestBodyObj != null ? requestBodyObj.contentType() : null,
                    contentLength,
                    response.code(),
                    response.message(),
                    response.headers(),
                    body);
        }
    }
}
