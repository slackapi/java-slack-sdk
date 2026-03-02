package com.slack.api.model.work_objects.external;

import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ButtonProcessingState {
    @Required Boolean enabled;
    String interstitialText;
}
