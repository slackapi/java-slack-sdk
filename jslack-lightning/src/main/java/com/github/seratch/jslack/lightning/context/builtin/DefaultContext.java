package com.github.seratch.jslack.lightning.context.builtin;

import com.github.seratch.jslack.lightning.context.Context;
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
