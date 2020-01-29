package com.slack.api.methods.response.admin.emoji;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.admin.Emoji;
import lombok.Data;

import java.util.Map;

@Data
public class AdminEmojiListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Map<String, Emoji> emoji;
    private ResponseMetadata responseMetadata;
}