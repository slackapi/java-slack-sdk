package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays actions as contextual info, which can include both feedback buttons and icon buttons.
 * https://docs.slack.dev/reference/block-kit/blocks/context-actions-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContextActionsBlock implements LayoutBlock {
    public static final String TYPE = "context_actions";
    private final String type = TYPE;
    @Builder.Default
    private List<ContextActionsBlockElement> elements = new ArrayList<>();
    private String blockId;
}
