package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.ActionRespondUtility;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.SayUtility;
import com.slack.api.bolt.response.Responder;
import lombok.*;

/**
 * Action type request's context from attachments in messages.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class AttachmentActionContext extends Context implements SayUtility, ActionRespondUtility {

    private String triggerId;
    private String channelId;
    private String responseUrl;
    private Responder responder;
}
