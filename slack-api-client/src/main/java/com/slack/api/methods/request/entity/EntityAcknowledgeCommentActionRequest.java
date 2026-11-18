package com.slack.api.methods.request.entity;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.EntityComment;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/entity.acknowledgeCommentAction
 */
@Data
@Builder
public class EntityAcknowledgeCommentActionRequest implements SlackApiRequest {

    /**
     * Authentication token.
     */
    private String token;

    /**
     * A reference to the original user action that initiated the comment mutation
     */
    private String triggerId;

    /**
     * The full comment data. Required for edit and post actions. See the [comment schema](https://docs.slack.dev/messaging/work-objects-comments#entity-present-comments-method) for the full list of properties.
     */
    private EntityComment comment;

    /**
     * Error message if the action failed in the app. When present, signals that the mutation could not be completed.
     */
    private String error;

}
