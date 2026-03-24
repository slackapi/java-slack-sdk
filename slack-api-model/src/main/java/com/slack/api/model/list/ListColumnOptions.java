package com.slack.api.model.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListColumnOptions {

    private List<Choice> choices; // select
    private String format; // select, channel
    private String defaultValue; // select
    private DefaultValue defaultValueTyped; // select
    private String emoji; // rating, vote
    private Integer max; // rating
    private Integer precision; // number
    private Boolean showMemberName; // channel
    private String dateFormat;
    private String timeFormat;
    private String currencyFormat;
    private String emojiTeamId;
    private String currency;
    private String rounding;
    private Boolean markAsDoneWhenChecked;
    private Boolean forAssignment;
    private Boolean notifyUsers;
    private List<String> linkedTo;
    private String canvasId;
    private List<CanvasPlaceholderMapping> canvasPlaceholderMapping;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DefaultValue {
        private List<String> select;
        private List<String> user;
        private List<String> channel;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private String value;
        private String label;
        private String color;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CanvasPlaceholderMapping {
        private String variable;
        private String column;
    }
}
