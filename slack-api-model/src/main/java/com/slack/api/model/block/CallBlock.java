package com.slack.api.model.block;

import com.slack.api.model.Call;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * https://api.slack.com/reference/messaging/blocks#call
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallBlock implements LayoutBlock {
    public static final String TYPE = "call";
    private final String type = TYPE;
    private String blockId;
    private String callId;
    private Boolean apiDecorationAvailable;
    private Map<String, Call> call;
}
