package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.SayUtility;
import lombok.*;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class EventContext extends Context implements SayUtility {

    private String channelId;

}
