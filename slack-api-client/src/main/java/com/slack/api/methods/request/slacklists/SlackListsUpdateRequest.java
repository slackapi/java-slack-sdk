package com.slack.api.methods.request.slacklists;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.block.RichTextBlock;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackLists.update
 */
@Data
@Builder
public class SlackListsUpdateRequest implements SlackApiRequest {

     /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.`
     */
    private String token;

    /**
     * The ID of the List to update.
     */
    private String id;

    /**
     * Name of the List. (Optional)
     */
    private String name;

    /**
     * A rich text description of the List. (Optional)
     */
    private List<RichTextBlock> descriptionBlocks;

    /**
     * Boolean indicating whether the List should be used to track todo tasks. (Optional)
     */
    private Boolean todoMode;
}