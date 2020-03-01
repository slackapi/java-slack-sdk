package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.ActionRespondUtility;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.response.Responder;
import lombok.*;

/**
 * Action type request's context.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ActionContext extends Context implements ActionRespondUtility {

    private String triggerId;
    private String responseUrl;
    private Responder responder;
}
