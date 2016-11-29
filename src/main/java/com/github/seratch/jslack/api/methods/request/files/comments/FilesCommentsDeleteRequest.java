package com.github.seratch.jslack.api.methods.request.files.comments;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilesCommentsDeleteRequest implements SlackApiRequest {

    private String token;
    private String file;
    private String id;
}