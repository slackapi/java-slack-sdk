package com.slack.api.model.list;

import com.slack.api.model.File;
import com.slack.api.model.Message;
import com.slack.api.model.block.RichTextBlock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListRecord {
    private String id;
    private String listId;
    private List<Field> fields;
    private Integer dateCreated;
    private String createdBy;
    private String threadTs;
    private String position;
    private String updatedTimestamp;
    private String updatedBy;
    private Map<String, String> viewPositions;
    private PlatformRefs platformRefs;
    private Boolean isSubscribed;
    private File.Saved saved;
    private Map<String, File.Saved> savedFields;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Field {
        private String key;
        private String columnId;
        private String value;
        private String text;
        private List<RichTextBlock> richText;
        private Message message;
        private List<Double> number;
        private List<String> select;
        private List<String> date;
        private List<String> user;
        private List<String> attachment;
        private Boolean checkbox;
        private List<String> email;
        private List<String> phone;
        private List<String> channel;
        private List<Integer> rating;
        private List<Integer> timestamp;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlatformRefs {
        private String botCreatedBy;
        private String botUpdatedBy;
        private String botDeletedBy;
    }
}
