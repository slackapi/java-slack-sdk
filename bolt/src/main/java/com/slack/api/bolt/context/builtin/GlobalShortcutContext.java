package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.Context;
import lombok.*;

/**
 * Action type request's context in messages.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class GlobalShortcutContext extends Context {
    private String triggerId;
}
