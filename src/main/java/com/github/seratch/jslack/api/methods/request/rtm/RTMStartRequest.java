package com.github.seratch.jslack.api.methods.request.rtm;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

/**
 * @see <a href="https://api.slack.com/methods/rtm.start">rtm.start</a>
 */
@Data
@Builder
public class RTMStartRequest implements SlackApiRequest {

    private String token;
    private Boolean noUnreads;
    private Boolean mpimAware;
}