package com.slack.api.methods.response.openid.connect;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://api.slack.com/methods/openid.connect.token
 */
@Data
public class OpenIDConnectTokenResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String accessToken;
    private String tokenType;
    private String idToken;
    private String refreshToken; // only when enabling token rotation
    private Integer expiresIn; // in seconds; only when enabling token rotation

}
