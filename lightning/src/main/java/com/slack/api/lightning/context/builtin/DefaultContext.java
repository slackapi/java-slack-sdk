package com.slack.api.lightning.context.builtin;

import com.slack.api.lightning.context.Context;
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
