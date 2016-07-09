package com.github.seratch.jslack.api.methods.request.files.comments;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilesCommentsEditRequest {

    private String token;
    private String file;
    private String id;
    private String comment;
}