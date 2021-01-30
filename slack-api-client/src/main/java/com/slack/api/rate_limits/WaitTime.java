package com.slack.api.rate_limits;

import com.slack.api.rate_limits.metrics.RequestPace;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WaitTime {
    private long millisToWait;
    private RequestPace pace;
}
