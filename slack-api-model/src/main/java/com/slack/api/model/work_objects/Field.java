package com.slack.api.model.work_objects;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.ImageBlock;
import com.slack.api.model.block.InputBlock;
import com.slack.api.model.block.RichTextBlock;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Field {
    @Required String type;
    @Required String label;
    @Required String fieldType;

    /**
     * The name of the field from {@link com.slack.api.model.EntityMetadata}.
     */
    String fieldName;

    /**
     * Plain text.
     */
    String text;

    /**
     * Rich text block.
     */
    RichTextBlock richText;

    /**
     * List of Unix timestamps.
     */
    List<Integer> timestamp;

    /**
     * List of image blocks.
     */
    List<ImageBlock> image;

    /**
     * Should the field take up the full width.
     */
    @SerializedName("long")
    Boolean isLong;

    /**
     * List of Slack and external users.
     */
    List<User> user;

    /**
     * List of Slack users.
     * @deprecated - prefer {@link this#user} field instead since this represents both Slack and external users.
     */
    @Deprecated
    List<SlackUser> slackUser;

    List<Tag> tag;

    InputBlock input;

    /**
     * Array of input blocks for editing.  Used when a field has multiple edit inputs.
     */
    List<InputBlock> inputs;

    /***
     * List of "YYYY-MM-DD dates".
     */
    List<String> date;

    /**
     * List of channels.
     */
    List<Channel> channel;

    /**
     * List of entity references.
     */
    List<EntityReference> entityRef;

    /**
     * List of checkbox options.
     */
    List<CheckboxOption> checkbox;

    /**
     * List of email addresses.
     */
    List<String> email;

    /**
     * List of links.
     */
    List<String> link;

    /**
     * Header with optional badge.
     */
    HeaderWithBadge headerWithBadge;

    /**
     * List of files.
     */
    List<File> file;

    /**
     * List of date time ranges.
     */
    List<DateTimeRange> dateTimeRange;

    /**
     * Represents the native value for boolean field types.
     */
    Boolean booleanValue;
}
