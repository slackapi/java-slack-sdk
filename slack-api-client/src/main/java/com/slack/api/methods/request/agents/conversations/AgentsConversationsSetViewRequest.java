package com.slack.api.methods.request.agents.conversations;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.block.LayoutBlock;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/agents.conversations.setView
 */
@Data
@Builder
public class AgentsConversationsSetViewRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the code channel to render the view in.
     */
    private String channelId;

    /**
     * The kind of view to create or update. Defaults to html. Determines which other arguments are required: html and
     * diff require content, block_kit requires blocks, canvas requires canvas_id, pull_request requires pr_url.
     */
    private String type;

    /**
     * Agent-assigned stable identity for the view (e.g. the source file path on the agent's machine). Used as the
     * upsert key: calls with the same view_key update the existing view.
     */
    private String viewKey;

    /**
     * View content. For html, a full self-contained HTML document; for diff, raw unified diff text. Capped at
     * 1,000,000 bytes.
     */
    private String content;

    /**
     * Block Kit blocks to render in the view tab. Required when type is block_kit; ignored otherwise.
     */
    private List<LayoutBlock> blocks;

    /**
     * Block Kit blocks to render in the view tab, as a JSON-encoded string. Required when type is block_kit; ignored
     * otherwise.
     */
    private String blocksAsString;

    /**
     * Encoded ID of the canvas to attach as the view. Required when type is canvas; ignored otherwise.
     */
    private String canvasId;

    /**
     * For canvas views: access level granted to the channel for the canvas tab. Defaults to write. Use 'comment' to
     * grant channel members comment access.
     */
    private String accessLevel;

    /**
     * For canvas views: hash of the canvas-derived markdown the agent last wrote, recorded so the agent can later
     * detect human edits to the canvas.
     */
    private String agentContentHash;

    /**
     * For pull_request views: the pull request's URL. Required when type is pull_request; ignored otherwise.
     */
    private String prUrl;

    /**
     * For diff views: base branch name for display purposes.
     */
    private String baseBranch;

    /**
     * For diff views: head branch name for display purposes.
     */
    private String headBranch;

    /**
     * Display label for the view tab. Preferred over the legacy 'label' argument (name wins if both are supplied).
     * Defaults to the last path segment of view_key.
     */
    private String name;

    /**
     * Deprecated alias for 'name'. Display label for the view tab. Defaults to the last path segment of view_key,
     * stripped of any .html/.htm extension.
     */
    @Deprecated
    private String label;

    /**
     * Content-Security-Policy domain declarations for the view. Domains are validated server-side (https-only, no
     * private/internal hosts) and persisted.
     */
    private Csp csp;

    /**
     * Content-Security-Policy domain declarations for the view, as a JSON-encoded string. Domains are validated
     * server-side (https-only, no private/internal hosts) and persisted.
     */
    private String cspAsString;

    @Data
    @Builder
    public static class Csp {
        private List<String> resourceDomains;
    }

}
