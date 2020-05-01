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
public class RichTextSectionElement extends BlockElement implements RichTextElement {
    public static final String TYPE = "rich_text_section";
    private final String type = TYPE;
    @Builder.Default
    private List<RichTextElement> elements = new ArrayList<>();

    // -------------------------------
    // Elements
    // -------------------------------

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Text implements RichTextElement {
        public static final String TYPE = "text";
        private final String type = TYPE;
        private String text;
        private TextStyle style;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Channel implements RichTextElement {
        public static final String TYPE = "channel";
        private final String type = TYPE;
        private String channelId; // C12345678
        private TextStyle style;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User implements RichTextElement {
        public static final String TYPE = "user";
        private final String type = TYPE;
        private String userId; // W12345678
        private TextStyle style;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Emoji implements RichTextElement {
        public static final String TYPE = "emoji";
        private final String type = TYPE;
        private String name;
        private Integer skinTone;
        private TextStyle style;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Link implements RichTextElement {
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
    public static class Team implements RichTextElement {
        public static final String TYPE = "team";
        private final String type = TYPE;
        private String teamId;
        private TextStyle style;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserGroup implements RichTextElement {
        public static final String TYPE = "usergroup";
        private final String type = TYPE;
        private String usergroupId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Date implements RichTextElement {
        public static final String TYPE = "date";
        private final String type = TYPE;
        private String timestamp;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Broadcast implements RichTextElement {
        public static final String TYPE = "broadcast";
        private final String type = TYPE;
        private String range; // channel, here, ..
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Color implements RichTextElement {
        public static final String TYPE = "color";
        private final String type = TYPE;
        private String value;
    }

    // -------------------------------

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TextStyle {
        private boolean bold;
        private boolean italic;
        private boolean strike;
        private boolean code;
    }

}
