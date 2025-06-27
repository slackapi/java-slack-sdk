package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    /**
     * Represents the type of action (e.g. Message button or message menu)
     *
     * @see <a href="https://docs.slack.dev/interactivity">Interaction Types</a>
     */
    public enum Type {

        /**
         * @see <a href="https://docs.slack.dev/legacy/legacy-messaging/legacy-message-buttons">Message button</a>
         */
        @SerializedName("button")
        BUTTON("button"),

        /**
         * @see <a href="https://docs.slack.dev/legacy/legacy-messaging/legacy-adding-menus-to-messages">Message menus</a>
         */
        @SerializedName("select")
        SELECT("select");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    private String id;
    private String name;
    private String text;
    private String style;
    @Builder.Default
    private Type type = Type.BUTTON;
    private String value;
    private Confirmation confirm;
    @Builder.Default
    private List<Option> options = new ArrayList<>();
    @Builder.Default
    private List<Option> selectedOptions = new ArrayList<>();
    private String dataSource;
    private Integer minQueryLength;
    private List<OptionGroup> optionGroups;
    private String url;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionGroup {
        private String text;
        private List<Option> options;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option {
        private String text;
        private String value;
    }
}
