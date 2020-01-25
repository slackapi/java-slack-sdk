package com.github.seratch.jslack.api.methods.request.views;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.slack.api.model.view.View;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewsPublishRequest implements SlackApiRequest {
    private String token;
    private View view;
    private String viewAsString;
    private String userId;
    private String hash;
}
