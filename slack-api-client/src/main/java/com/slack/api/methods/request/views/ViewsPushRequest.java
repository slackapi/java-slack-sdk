package com.slack.api.methods.request.views;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.view.View;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/views.push
 */
@Data
@Builder
public class ViewsPushRequest implements SlackApiRequest {

    private String token;
    private String triggerId;
    private String interactivityPointer;
    private View view;
    private String viewAsString;
}
