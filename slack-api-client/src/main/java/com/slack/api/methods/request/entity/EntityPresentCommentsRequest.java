package com.slack.api.methods.request.entity;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.EntityComment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/entity.presentComments
 */
@Data
@Builder
public class EntityPresentCommentsRequest implements SlackApiRequest {

    /**
     * Authentication token.
     */
    private String token;

    /**
     * Array of comments to present to the user. See the [comment schema](https://docs.slack.dev/messaging/work-objects-comments#entity-present-comments-method) for the full list of properties.
     */
    private List<EntityComment> comments;

    /**
     * A reference to the original user action that initiated the request
     */
    private String triggerId;

    /**
     * App supplied cursor used for pagination, will be sent in the next request for comments
     */
    private String cursor;

    /**
     * Indicates whether the user has permissions to post comments
     */
    private Boolean canPostComment;

    /**
     * The block action id that will be sent when a delete request is initiated for a comment.
     */
    private String deleteActionId;

    /**
     * Set to true (or 1) to indicate that the user must authenticate to see the comments data.
     */
    private Boolean userAuthRequired;

    /**
     * A custom URL to which users are directed for authentication if required.
     */
    private String userAuthUrl;

    /**
     * Error indicating why the comments cannot be provided. It takes the same object as `entity.presentDetails`.
     */
    private EntityPresentDetailsRequest.Error error;

}
