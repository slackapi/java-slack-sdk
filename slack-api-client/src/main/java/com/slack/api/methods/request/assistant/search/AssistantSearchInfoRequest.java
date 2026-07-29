package com.slack.api.methods.request.assistant.search;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/assistant.search.info
 */
@Data
@Builder
public class AssistantSearchInfoRequest implements SlackApiRequest {

    private String token;
}
