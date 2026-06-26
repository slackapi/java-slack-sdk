package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A single segment of a pie chart within a {@link DataVisualizationChart}.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-visualization-block">Data visualization block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataVisualizationSegment {
    /**
     * The legend/hover text for the segment. Maximum 20 characters.
     */
    private String label;

    /**
     * The weight of the segment. Must be greater than 0.
     */
    private Double value;
}
