package com.github.seratch.jslack.api.methods.request.api;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiTestRequest implements SlackApiRequest {

    private String foo;
    private String error;
}