package com.slack.api.lightning.context.builtin;

import com.slack.api.lightning.context.Context;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class OAuthCallbackContext extends Context {

    private String oauthCompletionUrl;
    private String oauthCancellationUrl;

}
