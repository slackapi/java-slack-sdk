package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The axis configuration of a bar, area, or line {@link DataVisualizationChart}.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block">Data visualization block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataVisualizationAxisConfig {
    /**
     * The x-axis labels that define the display order of categories. Each maximum 20 characters.
     */
    private List<String> categories;

    /**
     * The x-axis title. Maximum 50 characters.
     */
    private String xLabel;

    /**
     * The y-axis title. Maximum 50 characters.
     */
    private String yLabel;
}
