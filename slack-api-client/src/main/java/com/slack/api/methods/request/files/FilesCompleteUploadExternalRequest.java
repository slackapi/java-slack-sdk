package com.slack.api.methods.request.files;

import com.slack.api.methods.SlackApiRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://api.slack.com/methods/files.completeUploadExternal
 */
@Data
@Builder
public class FilesCompleteUploadExternalRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `files:write`
     */
    private String token;

    /**
     * Array of file ids and their corresponding (optional) titles.
     */
    private List<FileDetails> files;

    /**
     * Channel ID where the file will be shared. If not specified the file will be private.
     */
    private String channelId;

    /**
     * Comma-separated string of channel IDs where the file will be shared.
     */
    private List<String> channels;

    /**
     * The message text introducing the file in specified channels.
     */
    private String initialComment;

    /**
     * Provide another message's ts value to upload this file as a reply.
     * Never use a reply's ts value; use its parent instead.
     */
    private String threadTs;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileDetails {
        private String id; // required
        private String title; // optional
    }

}