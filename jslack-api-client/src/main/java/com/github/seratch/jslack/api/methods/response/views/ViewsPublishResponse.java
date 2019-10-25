package com.github.seratch.jslack.api.methods.response.views;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.view.View;
import lombok.Data;

import java.util.List;

@Data
public class ViewsPublishResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private View view;

    private ResponseMetadata responseMetadata;

    @Data
    public static class ResponseMetadata {
        private List<String> messages;
    }
}