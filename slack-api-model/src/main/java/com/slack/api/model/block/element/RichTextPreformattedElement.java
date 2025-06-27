package com.slack.api.model.block.element;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.slack.dev/changelog/2019/09/01/what-they-see-is-what-you-get-and-more-and-less
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RichTextPreformattedElement extends BlockElement implements RichTextElement {

    public static final String TYPE = "rich_text_preformatted";
    private final String type = TYPE;
    @Builder.Default
    private List<RichTextElement> elements = new ArrayList<>();
    private Integer border;

}
