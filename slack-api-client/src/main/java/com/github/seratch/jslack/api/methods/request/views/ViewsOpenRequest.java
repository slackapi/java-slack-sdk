package com.github.seratch.jslack.api.methods.request.views;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.slack.api.model.view.View;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewsOpenRequest implements SlackApiRequest {
    private String token;
    private String triggerId;
    private View view;
    private String viewAsString;
}
