package com.slack.api.methods.request.views;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.view.View;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/views.update
 */
@Data
@Builder
public class ViewsUpdateRequest implements SlackApiRequest {
    private String token;
    private View view;
    private String viewAsString;
    private String externalId;
    private String hash;
    private String viewId;
}
