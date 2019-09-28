package com.github.seratch.jslack.api.methods.request.views;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.model.view.View;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewsPushRequest implements SlackApiRequest {

    private String token;
    private String triggerId;
    private View view;
}
