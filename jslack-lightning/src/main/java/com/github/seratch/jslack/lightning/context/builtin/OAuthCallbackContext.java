package com.github.seratch.jslack.lightning.context.builtin;

import com.github.seratch.jslack.lightning.context.Context;
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
