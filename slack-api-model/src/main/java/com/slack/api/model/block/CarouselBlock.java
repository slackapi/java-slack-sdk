package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A carousel is a layout block used to display a horizontally scrollable collection of
 * {@link CardBlock cards}. It must contain between 1 and 10 cards.
 *
 * https://docs.slack.dev/reference/block-kit/blocks/carousel-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarouselBlock implements LayoutBlock {
    public static final String TYPE = "carousel";
    private final String type = TYPE;

    /**
     * An array of {@link CardBlock card} blocks. Must contain between 1 and 10 cards.
     */
    @Builder.Default
    private List<CardBlock> elements = new ArrayList<>();

    private String blockId;
}
