package com.slack.api.methods.request.views;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.view.View;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/views.publish
 */
@Data
@Builder
public class ViewsPublishRequest implements SlackApiRequest {
    private String token;
    private View view;
    private String viewAsString;
    private String userId;
    private String hash;
}
