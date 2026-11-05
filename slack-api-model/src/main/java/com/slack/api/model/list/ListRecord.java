package com.slack.api.model.list;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.File;
import com.slack.api.model.block.RichTextBlock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Field {
        private String key;
        @SerializedName("column_id")
        private String columnId;
        private String value;
        private String text;
        @SerializedName("rich_text")
        private List<RichTextBlock> richText;
        // The message field is an array of message references, verified against the live
        // API (slackLists.items.list and the conversations.replies/history nested
        // list_record path both return List<{value, channel_id, ts, thread_ts?}>).
        // EXPERIMENTAL: this replaces the earlier Message-typed modeling (#1590), which
        // did not match the actual response shape. See MessageRef.
        private List<MessageRef> message;
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
        private List<LinkField> link;
        private List<ReferenceField> reference;
    }

    /**
     * Message field reference for Slack Lists items. The API returns the message field as
     * an array of these references. Verified against the live API — every element carries
     * value/channel_id/ts, and thread_ts is present only when the referenced message is a
     * threaded reply.
     * EXPERIMENTAL: introduced to replace the earlier Message-typed modeling.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageRef {
        // Permalink URL of the referenced message.
        private String value;
        @SerializedName("channel_id")
        private String channelId;
        private String ts;
        @SerializedName("thread_ts")
        private String threadTs;
    }

    /**
     * Link field structure for Slack Lists items.
     * https://docs.slack.dev/reference/methods/slackLists.items.create#field-types
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkField {
        @SerializedName("original_url")
        private String originalUrl;
        @SerializedName("display_as_url")
        private Boolean displayAsUrl;
        @SerializedName("display_name")
        private String displayName;
    }

    /**
     * Reference field structure for Slack Lists items.
     * https://docs.slack.dev/reference/methods/slackLists.items.create#field-types
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReferenceField {
        private FileRef file;
    }

    /**
     * File reference within a ReferenceField.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileRef {
        @SerializedName("file_id")
        private String fileId;
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

    /**
     * Cell update structure for slackLists.items.update.
     * Includes row_id in addition to column_id and field value.
     * https://docs.slack.dev/reference/methods/slackLists.items.update
     */
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class CellUpdate extends Field {
        /**
         * The ID of the row to update.
         */
        @SerializedName("row_id")
        private String rowId;
    }
}
