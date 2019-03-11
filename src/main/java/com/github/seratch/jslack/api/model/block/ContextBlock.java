package com.github.seratch.jslack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://api.slack.com/reference/messaging/blocks#context
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContextBlock implements LayoutBlock {
    private final String type = "context";
    @Builder.Default
    private List<ContextBlockElement> elements = new ArrayList<>();
    private String blockId;
}
