package com.slack.api.rate_limits.queue;

import com.slack.api.rate_limits.WaitTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class QueueMessage<SUPPLIER> {
    private String id;
    private long millisToRun;
    private WaitTime waitTime;
    private SUPPLIER supplier;
}
