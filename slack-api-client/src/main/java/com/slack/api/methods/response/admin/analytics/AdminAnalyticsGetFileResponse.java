package com.slack.api.methods.response.admin.analytics;

import com.slack.api.methods.SlackApiBinaryResponse;
import lombok.Data;

@Data
public class AdminAnalyticsGetFileResponse implements SlackApiBinaryResponse {

    private byte[] file;

    @Override
    public byte[] asBytes() {
        return getFile();
    }
}