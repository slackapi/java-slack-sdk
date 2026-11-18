package com.slack.api.model;

import com.slack.api.model.block.RichTextBlock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A comment on a work object, shown in the comments tab of its flexpane.
 * https://docs.slack.dev/reference/methods/entity.presentComments#comment-schema
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityComment {

    /**
     * The comment content. Provide one of `comment.blocks` or `comment.value`.
     */
    private Content comment;

    /**
     * A unique ID sourced from your app and associated with the comment. It will be used if editing a comment.
     */
    private String id;

    /**
     * Same User type used in a Work Object's fields.
     */
    private EntityMetadata.EntityPayload.User sender;

    /**
     * Epoch timestamp in seconds.
     */
    private Integer timestamp;

    /**
     * If present and `true`, renders the delete button on that comment. When `true`, `delete_action_id` should also be present, otherwise the delete button will render but do nothing when clicked. Empty strings are not supported.
     */
    private Boolean canDelete;

    /**
     * If present and `true`, indicates that the comment is editable in the user's flexpane.
     */
    private Boolean canEdit;

    /**
     * If present and `true`, allows users to reply to this comment in a thread.
     */
    private Boolean canReply;

    /**
     * Pagination cursor associated with this comment's position in the list.
     */
    private String cursor;

    /**
     * Epoch timestamp in seconds of the last edit to this comment.
     */
    private Integer lastEditTs;

    /**
     * Epoch timestamp in seconds of the most recent reply in the thread.
     */
    private Integer latestReplyTs;

    /**
     * Number of replies in the thread under this comment.
     */
    private Integer replyCount;

    /**
     * Up to two users who have replied to this comment thread. Same User type as `sender`.
     */
    private List<EntityMetadata.EntityPayload.User> replyUsers;

    /**
     * The `id` of the parent comment. If present, this comment is a threaded reply.
     */
    private String threadRootId;

    /**
     * URL which directs to the comment.
     */
    private String url;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {

        /**
         * A rich text block containing the comment content.
         */
        private RichTextBlock blocks;

        /**
         * If set to `"markdown"`, the `comment.value` will be formatted as markdown.
         */
        private String format;

        /**
         * Plain or markdown string containing the comment content. This will be used if `blocks` is not present on the comment object.
         */
        private String value;
    }
}
