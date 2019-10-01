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
public class RichTextSectionElement extends BlockElement {
    public static final String TYPE = "rich_text_section";
    private final String type = TYPE;
    @Builder.Default
    private List<Element> elements = new ArrayList<>();

    public interface Element {
        String getType();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Text implements Element {
        public static final String TYPE = "text";
        private final String type = TYPE;
        private String text;
        private TextStyle style;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TextStyle {
        private boolean bold;
        private boolean italic;
        private boolean strike;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Emoji implements Element {
        public static final String TYPE = "emoji";
        private final String type = TYPE;
        private String name;
    }

}