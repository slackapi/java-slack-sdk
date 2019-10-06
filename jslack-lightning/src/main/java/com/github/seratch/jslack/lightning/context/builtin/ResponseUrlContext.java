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
public class ResponseUrlContext extends Context {

    private String responseUrl;
}
