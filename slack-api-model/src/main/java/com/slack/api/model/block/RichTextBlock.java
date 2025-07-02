package com.slack.api.model.block;

import com.slack.api.model.block.element.BlockElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.slack.dev/changelog/2019/09/01/what-they-see-is-what-you-get-and-more-and-less
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichTextBlock implements LayoutBlock {
    public static final String TYPE = "rich_text";
    private final String type = TYPE;
    @Builder.Default
    private List<BlockElement> elements = new ArrayList<>();
    private String blockId;
}
