package com.github.seratch.jslack.api.methods.response.emoji;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.Map;

@Data
public class EmojiListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private Map<String, String> emoji;
}