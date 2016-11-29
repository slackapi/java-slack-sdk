package com.github.seratch.jslack.api.methods.request.im;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImOpenRequest implements SlackApiRequest {

    private String token;
    private String user;
    private boolean returnIm;
}