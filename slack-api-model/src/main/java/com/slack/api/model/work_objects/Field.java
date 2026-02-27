package com.slack.api.model.work_objects;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.ImageBlock;
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

    String text;

    RichTextBlock richText;

    /**
     * List of Unix timestamps.
     */
    List<Integer> timestamp;

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

    List<SlackUser> slackUser;
}
