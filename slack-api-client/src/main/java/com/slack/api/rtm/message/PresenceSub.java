package com.slack.api.rtm.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/reference/events/presence_sub
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PresenceSub implements RTMMessage {

    public static final String TYPE_NAME = "presence_sub";

    private final String type = TYPE_NAME;
    private List<String> ids;
}
