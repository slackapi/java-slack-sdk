package com.slack.api.methods.request.files;

import com.slack.api.methods.SlackApiRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

/**
 * Wrapper method to enable developers to easily use the new way of uploading files.
 * To mitigate the confusion among existing users of files.upload API,
 * this method internally performs a few HTTP requests.
 * <p>
 * - step1: https://api.slack.com/methods/files.getUploadURLExternal (per file)
 * - step2: POST requests to upload_url (per file)
 * - step3: https://api.slack.com/methods/files.completeUploadExternal (only once)
 * <p>
 * Although most of the parameters are compatible, there are few difference.
 * Unlike files.upload API, this new way allows developers to upload multiple files at a time.
 * Also, the following operations are no longer supported.
 * - Share the uploaded files in multiple channels
 * - Set filetype for a file
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilesUploadV2Request implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `files:write`
     */
    private String token;

    /**
     * File contents via `multipart/form-data`. If omitting this parameter, you must submit `content`.
     * (this is mainly for backward compatibility - using uploadFiles instead is recommended)
     */
    private File file;
    /**
     * File contents via `multipart/form-data`. If omitting this parameter, you must submit `content`.
     * (this is mainly for backward compatibility - using uploadFiles instead is recommended)
     */
    private byte[] fileData;
    /**
     * File contents via a POST variable. If omitting this parameter, you must provide a `file`.
     * (this is mainly for backward compatibility - using uploadFiles instead is recommended)
     */
    private String content;
    /**
     * Name of the file being uploaded.
     * (this is mainly for backward compatibility - using uploadFiles instead is recommended)
     */
    private String filename;
    /**
     * Title of the file, which is visible in the Slack UI.
     * (this is mainly for backward compatibility - using uploadFiles instead is recommended)
     */
    private String title;
    /**
     * Description of image for screen-reader.
     * (this is mainly for backward compatibility - using uploadFiles instead is recommended)
     */
    private String altTxt;
    /**
     * Syntax type of the snippet being uploaded.
     * (this is mainly for backward compatibility - using uploadFiles instead is recommended)
     */
    private String snippetType;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadFile {
        /**
         * File contents via `multipart/form-data`. If omitting this parameter, you must submit `content`.
         */
        private File file;
        /**
         * File contents via `multipart/form-data`. If omitting this parameter, you must submit `content`.
         */
        private byte[] fileData;
        /**
         * File contents via a POST variable. If omitting this parameter, you must provide a `file`.
         */
        private String content;
        /**
         * Name of the file being uploaded.
         */
        private String filename;
        /**
         * Title of the file, which is visible in the Slack UI.
         */
        private String title;
        /**
         * Description of image for screen-reader.
         */
        private String altTxt;
        /**
         * Syntax type of the snippet being uploaded.
         */
        private String snippetType;
    }

    /**
     * Multiple files to upload
     */
    private List<UploadFile> uploadFiles;

    /**
     * The message text introducing the file in specified channel.
     */
    private String initialComment;

    /**
     * Channel ID where the file will be shared. If not specified the file will be private.
     */
    private String channel;

    /**
     * Provide another message's ts value to upload this file as a reply.
     * Never use a reply's ts value; use its parent instead.
     */
    private String threadTs;

}
