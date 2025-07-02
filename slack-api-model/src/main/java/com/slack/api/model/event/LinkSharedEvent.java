package com.slack.api.model.event;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * The thread_ts field only appears when the link was shared within a message thread.
 * <p>
 * https://docs.slack.dev/reference/events/link_shared
 */
@Data
public class LinkSharedEvent implements Event {

    public static final String TYPE_NAME = "link_shared";

    private final String type = TYPE_NAME;
    private String channel; // This can be "COMPOSER"
    private String user;
    private String messageTs;
    private String threadTs;
    private List<Link> links;
    @SerializedName("is_bot_user_member")
    private boolean botUserMember;

    // https://docs.slack.dev/changelog/2021-08-changes-to-unfurls
    private String unfurlId;
    // https://docs.slack.dev/changelog/2021-08-changes-to-unfurls
    private String source; // "composer" / "conversations_history"

    private String eventTs;

    @Data
    public static class Link {
        private String domain;
        private String url;
    }
}