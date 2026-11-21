package com.slack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/data-table-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnknownTableCell implements TableCell {
    private String type;
}
