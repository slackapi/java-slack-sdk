package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.Context;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
public class OAuthCallbackContext extends Context {

    private String oauthCompletionUrl;
    private String oauthCancellationUrl;

}
