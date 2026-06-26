package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data visualization is a layout block used to render a chart from supplied data. The chart may be
 * a pie, bar, area, or line chart, configured via the {@link DataVisualizationChart chart} object.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block">Data visualization block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataVisualizationBlock implements LayoutBlock {
    public static final String TYPE = "data_visualization";
    private final String type = TYPE;

    /**
     * The label displayed above the chart. Maximum 50 characters.
     */
    private String title;

    /**
     * The chart to render. One of pie, bar, area, or line.
     */
    private DataVisualizationChart chart;

    private String blockId;
}
