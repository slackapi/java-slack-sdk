package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A single data point of a {@link DataVisualizationSeries}.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block">Data visualization block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataVisualizationDataPoint {
    /**
     * The x-axis category. Must match a category defined in the chart's
     * {@link DataVisualizationAxisConfig axis config}. Maximum 20 characters.
     */
    private String label;

    /**
     * The y-axis value. Negative values are permitted.
     */
    private Double value;
}
