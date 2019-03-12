package com.github.seratch.jslack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://api.slack.com/reference/messaging/composition-objects#option-group
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionGroupObject {
    private PlainTextObject label;
    @Builder.Default
    private List<OptionObject> options = new ArrayList<>();
}
