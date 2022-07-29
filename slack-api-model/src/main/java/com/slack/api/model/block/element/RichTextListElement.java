package com.slack.api.model.block.element;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * https://api.slack.com/changelog/2019-09-what-they-see-is-what-you-get-and-more-and-less
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RichTextListElement extends BlockElement implements RichTextElement {

    public static final String TYPE = "rich_text_list";
    private final String type = TYPE;
    @Builder.Default
    private List<RichTextElement> elements = new ArrayList<>();
    private String style; // bullet, ordered
    private Integer indent;
    private Integer offset;
    private Integer border;
}
