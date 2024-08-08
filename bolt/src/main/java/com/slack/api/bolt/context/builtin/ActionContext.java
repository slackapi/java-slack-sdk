package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.ActionRespondUtility;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.FunctionUtility;
import com.slack.api.bolt.util.Responder;
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
public class ActionContext extends Context implements ActionRespondUtility, FunctionUtility {

    private String triggerId;
    private String responseUrl;
    private Responder responder;
}
