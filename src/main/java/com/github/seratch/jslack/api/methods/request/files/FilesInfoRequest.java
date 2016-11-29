package com.github.seratch.jslack.api.methods.request.files;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilesInfoRequest implements SlackApiRequest {

    private String token;
    private String file;
    private Integer count;
    private Integer page;
}