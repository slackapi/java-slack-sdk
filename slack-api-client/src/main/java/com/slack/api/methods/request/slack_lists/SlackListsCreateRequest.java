package com.slack.api.methods.request.slack_lists;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.block.RichTextBlock;
import com.slack.api.model.list.ListColumn;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackLists.create
 */
@Data
@Builder
public class SlackListsCreateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.`
     */
    private String token;

    /**
     * Name of the List.
     */
    private String name;

    /**
     * A rich text description of the List. (Optional)
     */
    private List<RichTextBlock> descriptionBlocks;

    /**
     * Column definition for the List. (Optional)
     */
    private List<ListColumn> schema;

    /**
     * ID of the List to copy. (Optional)
     */
    private String copyFromListId;

    /**
     * Boolean indicating whether to include records when a List is copied. (Optional)
     */
    private Boolean includeCopiedListRecords;

    /**
     * Boolean indicating whether the List should be used to track todo tasks. (Optional)
     */
    private Boolean todoMode;
}