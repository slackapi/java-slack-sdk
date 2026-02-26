package com.slack.api.model.work_objects;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.ImageBlock;
import com.slack.api.model.block.RichTextBlock;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Field {
    @Required private String type;
    @Required private String label;
    @Required private String fieldType;

    /**
     * The name of the field from {@link com.slack.api.model.EntityMetadata}.
     */
    private String fieldName;

    private String text;

    private RichTextBlock richText;

    /**
     * List of Unix timestamps.
     */
    private List<Integer> timestamp;

    private List<ImageBlock> image;

    /**
     * Should the field take up the full width.
     */
    @SerializedName("long")
    private Boolean isLong;

    /**
     * List of Slack and external users.
     */
    private List<User> user;

    private List<SlackUser> slackUser;
}
