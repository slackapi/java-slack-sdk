package com.github.seratch.jslack.api.methods.request.files;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.List;

@Data
@Builder
public class FilesUploadRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `files:write:user`
     */
    private String token;

    /**
     * File contents via `multipart/form-data`. If omitting this parameter, you must submit `content`.
     */
    private File file;

    /**
     * File contents via a POST variable. If omitting this parameter, you must provide a `file`.
     */
    private String content;

    /**
     * A [file type](/types/file#file_types) identifier.
     */
    private String filetype;

    /**
     * Filename of file.
     */
    private String filename;

    /**
     * Title of file.
     */
    private String title;

    /**
     * Initial comment to add to file.
     */
    private String initialComment;

    /**
     * Comma-separated list of channel names or IDs where the file will be shared.
     */
    private List<String> channels;

}