package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A single data series of a bar, area, or line chart within a {@link DataVisualizationChart}.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block">Data visualization block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataVisualizationSeries {
    /**
     * The legend identifier for the series. Must be unique per chart. Maximum 20 characters.
     */
    private String name;

    /**
     * The data points of the series, one per category. Between 1 and 20 items.
     */
    private List<DataVisualizationDataPoint> data;
}
