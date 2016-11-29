package com.github.seratch.jslack.api.methods.request.rtm;

import lombok.Builder;
import lombok.Data;

/**
 * @see <a href="https://api.slack.com/methods/rtm.start">rtm.start</a>
 */
@Data
@Builder
public class RTMStartRequest {

    private String token;
    private Boolean noUnreads;
    private Boolean mpimAware;
}