package com.github.seratch.jslack.api.model.block.element;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * https://api.slack.com/changelog/2019-09-what-they-see-is-what-you-get-and-more-and-less
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichTextPreformattedElement extends BlockElement implements RichTextElement {

    public static final String TYPE = "rich_text_preformatted";
    private final String type = TYPE;
    @Builder.Default
    private List<RichTextElement> elements = new ArrayList<>();

}