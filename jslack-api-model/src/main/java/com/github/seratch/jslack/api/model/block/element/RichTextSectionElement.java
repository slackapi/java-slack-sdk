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
    public static class Link implements Element {
        public static final String TYPE = "link";
        private final String type = TYPE;
        private String url;
        private String text;
        private TextStyle style;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User implements Element {
        public static final String TYPE = "user";
        private final String type = TYPE;
        private String user_id;
        private TextStyle style;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Team implements Element {
        public static final String TYPE = "team";
        private final String type = TYPE;
        private String team_id;
        private TextStyle style;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserGroup implements Element {
        public static final String TYPE = "usergroup";
        private final String type = TYPE;
        private String usergroup_id;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Date implements Element {
        public static final String TYPE = "date";
        private final String type = TYPE;
        private String timestamp;
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