package com.slack.api.model.block;

import com.slack.api.model.CallParticipant;
import com.slack.api.model.admin.AppIcons;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * https://api.slack.com/reference/messaging/blocks#call
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallBlock implements LayoutBlock {
    public static final String TYPE = "call";
    private final String type = TYPE;
    private String blockId;
    private String callId;
    private Boolean apiDecorationAvailable;
    private CallData call;

    @Data
    public class CallData {
        private Call v1;
        private String mediaBackendType;
    }

    @Data
    public class Call {
        private String id;
        private String appId;
        private AppIcons appIconUrls;
        private Integer dateStart;
        private List<CallParticipant> activeParticipants;
        private List<CallParticipant> allParticipants;
        private String displayId; // external_display_id in calls.* API response
        private String joinUrl;
        private String desktopAppJoinUrl;
        private String name; // title in calls.* API response
        private String createdBy; // Slack User ID
        private Integer dateEnd;
        private List<String> channels;
        private Boolean isDmCall;
        private Boolean wasRejected;
        private Boolean wasMissed;
        private Boolean wasAccepted;
        private Boolean hasEnded;
    }

}
