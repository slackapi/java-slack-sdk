package com.slack.api.model.block;

import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.TextObject;
import com.slack.api.model.block.element.ImageElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/container-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerBlock implements LayoutBlock {
    public static final String TYPE = "container";
    private final String type = TYPE;

    private PlainTextObject title;

    private RichTextBlock richTextTitle;

    private TextObject subtitle;

    @Builder.Default
    private List<LayoutBlock> childBlocks = new ArrayList<>();

    private String width;

    private ImageElement icon;

    private Boolean isCollapsible;

    private Boolean defaultCollapsed;

    private Boolean hasHeaderDivider;

    private String blockId;
}
