package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.Context;
import lombok.*;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class DefaultContext extends Context {

    public DefaultContext() {
    }
}
