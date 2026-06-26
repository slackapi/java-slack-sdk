package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The chart rendered by a {@link DataVisualizationBlock}.
 *
 * <p>For a pie chart ({@code type} = {@code pie}), supply {@code segments}. For a bar, area, or line
 * chart ({@code type} = {@code bar}, {@code area}, or {@code line}), supply {@code series} together
 * with an {@link DataVisualizationAxisConfig axis config}.</p>
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block">Data visualization block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataVisualizationChart {
    /**
     * The type of chart. One of {@code pie}, {@code bar}, {@code area}, or {@code line}.
     */
    private String type;

    /**
     * The segments of a pie chart. Required for pie charts; between 1 and 6 items.
     */
    private List<DataVisualizationSegment> segments;

    /**
     * The data series of a bar, area, or line chart. Required for those chart types; between 1 and 6 items.
     */
    private List<DataVisualizationSeries> series;

    /**
     * The axis configuration for a bar, area, or line chart. Required for those chart types.
     */
    private DataVisualizationAxisConfig axisConfig;
}
