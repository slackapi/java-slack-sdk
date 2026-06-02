package com.slack.api.methods.request.assistant.search;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/assistant.search.context
 */
@Data
@Builder
public class AssistantSearchContextRequest implements SlackApiRequest {

    private String token;

    /**
     * User prompt or search query.
     */
    private String query;

    /**
     * Send action_token as received in a message event.
     */
    private String actionToken;

    /**
     * Mix and match channel types by providing any combination of public_channel, private_channel, mpim, im.
     */
    private List<String> channelTypes;

    /**
     * Content types to include. Can include messages, files, channels, users.
     */
    private List<String> contentTypes;

    /**
     * Whether the results should include bots.
     */
    private Boolean includeBots;

    /**
     * Whether to include deleted users in the user search results.
     */
    private Boolean includeDeletedUsers;

    /**
     * UNIX timestamp filter. If present, filters for results before this date.
     */
    private Integer before;

    /**
     * UNIX timestamp filter. If present, filters for results after this date.
     */
    private Integer after;

    /**
     * Whether to include context messages surrounding the main message result.
     */
    private Boolean includeContextMessages;

    /**
     * Context channel ID to support scoping the search when applicable.
     */
    private String contextChannelId;

    /**
     * The cursor returned by the API.
     */
    private String cursor;

    /**
     * Number of results to return, up to a max of 20.
     */
    private Integer limit;

    /**
     * The field to sort the results by. Can be score or timestamp.
     */
    private String sort;

    /**
     * The direction to sort the results by. Can be asc or desc.
     */
    private String sortDir;

    /**
     * Whether to return the message blocks in the response.
     */
    private Boolean includeMessageBlocks;

    /**
     * Whether to highlight the search query in the results.
     */
    private Boolean highlight;

    /**
     * A list of keyword clauses used to match search results.
     */
    private List<List<String>> keywordsClauses;

    /**
     * A list of term clauses. Search results returned will match every term clause specified.
     */
    private List<String> termClauses;

    /**
     * A string containing only modifiers in the format of modifier:value.
     */
    private String modifiers;

    /**
     * Whether to include archived channels in the search results.
     */
    private Boolean includeArchivedChannels;

    /**
     * Whether to disable semantic search. When true, only keyword-based search is used.
     */
    private Boolean disableSemanticSearch;
}
