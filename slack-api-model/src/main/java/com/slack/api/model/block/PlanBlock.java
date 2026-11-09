package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Displays a collection of related tasks.
 *
 * https://docs.slack.dev/reference/block-kit/blocks/plan-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanBlock implements LayoutBlock {
    public static final String TYPE = "plan";

    /**
     * The type of block. For this block, type will always be {@code plan}.
     */
    private final String type = TYPE;

    /**
     * Title of the plan in plain text.
     */
    private String title;

    /**
     * A sequence of task card blocks, maximum of 50 tasks. Each one contains task-like objects without a type,
     * and each {@code task_id} in a plan must be unique.
     */
    private List<TaskCardBlock> tasks;

    /**
     * A unique identifier for a block. If not specified, one will be generated. Maximum length for this field is
     * 255 characters. {@code block_id} should be unique for each message and each iteration of a message. If a
     * message is updated, use a new {@code block_id}.
     */
    private String blockId;
}
