package com.slack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/composition-objects#option
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionObject {
    private PlainTextObject text;
    private String value;
}
