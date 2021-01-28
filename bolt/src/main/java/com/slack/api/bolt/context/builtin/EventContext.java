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

    // X-Slack-Retry-Num: 2 in HTTP Mode
    // "retry_attempt": 0, in Socket Mode
    private Integer retryNum;
    // X-Slack-Retry-Reason: http_error in HTTP Mode
    // "retry_reason": "timeout", in Socket Mode
    private String retryReason;

}
