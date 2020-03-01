package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.ActionRespondUtility;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.SayUtility;
import com.slack.api.bolt.response.Responder;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class DialogCancellationContext extends Context implements SayUtility, ActionRespondUtility {

    private String responseUrl;
    private String channelId;
    private Responder responder;
}
