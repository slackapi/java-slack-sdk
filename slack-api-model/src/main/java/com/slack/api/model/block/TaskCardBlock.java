package com.slack.api.model.block;

import com.slack.api.model.block.composition.SlackIconObject;
import com.slack.api.model.block.element.UrlSourceElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/task-card-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCardBlock implements LayoutBlock {
    public static final String TYPE = "task_card";
    private final String type = TYPE;

    private String taskId;
    private String title;
    private String status;
    private RichTextBlock details;
    private RichTextBlock output;
    private List<UrlSourceElement> sources;
    private String blockId;
    private SlackIconObject icon;
    private Boolean hideTitle;
}
