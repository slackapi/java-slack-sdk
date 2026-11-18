package com.slack.api.methods.response.admin.analytics.members;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminAnalyticsMembersActivityResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String date;
    private AvailableDateRange availableDateRange;
    private List<Member> members;
    private Integer totalCount;
    private ResponseMetadata responseMetadata;

    @Data
    public static class AvailableDateRange {
        private String startDate;
        private String endDate;
    }

    @Data
    public static class Member {
        private String userId;
        private String teamId;
        private String enterpriseId;
        private Integer dateCreated;
        private Integer dateClaimed;
        private Integer dateDeleted;
        @SerializedName("is_admin")
        private boolean admin;
        @SerializedName("is_owner")
        private boolean owner;
        @SerializedName("is_primary_owner")
        private boolean primaryOwner;
        @SerializedName("is_restricted")
        private boolean restricted;
        @SerializedName("is_ultra_restricted")
        private boolean ultraRestricted;
        @SerializedName("is_invited_member")
        private Boolean invitedMember;
        @SerializedName("is_invited_guest")
        private Boolean invitedGuest;
        private String accountType;
        private Integer dateLastActive;
        private Integer dateLastActiveDesktop;
        private Integer dateLastActiveAndroid;
        private Integer dateLastActiveIos;
        private Integer activeDayCount;
        private Integer activeOnDesktopDayCount;
        private Integer activeOnAndroidDayCount;
        private Integer activeOnIosDayCount;
        private Integer activeOnSlackConnectDayCount;
        private Integer messageCount;
        private Integer channelMessageCount;
        private Integer reactionCount;
        private Integer filesAddedCount;
        private Integer slackHuddlesCount;
        private Integer searchCount;
        private Integer maxChannelsJoinedCount;
        private Integer aiUsageCount;
        private Integer messagesSentToSlackbotCount;
        private Integer channelSummariesViewedCount;
        private Integer threadSummariesViewedCount;
        private Integer recapsViewedCount;
        private Integer aiHuddleNotesGeneratedCount;
        private Integer messageExplanationsViewedCount;
        private Integer fileSummariesViewedCount;
        private Integer keywordSearchSummariesViewedCount;
        private Integer naturalLanguageSearchSummariesViewedCount;
        private Integer translationsGeneratedCount;
        private Integer fileTranslationsGeneratedCount;
        private Integer messageTranslationsGeneratedCount;
        private Integer aiAssistedWorkflowDraftsCreatedCount;
        private Integer canvasesCreatedCount;
        private Integer canvasesViewedCount;
        private Integer canvasesEditedCount;
        private Integer commentsAddedToCanvasesCount;
        private Integer canvasesSharedCount;
        private Integer reactionsAddedToCanvasesCount;
        private Integer listsCreatedCount;
        private Integer listsViewedCount;
        private Integer listsEditedCount;
        private Integer commentsAddedToListsCount;
        private Integer listsSharedCount;
        private Integer slackbotSkillsCreatedCount;
        private Integer slackbotSkillUsageCount;
        private Integer clipsCreatedCount;
        private Integer videoClipsCreatedCount;
        private Integer audioClipsCreatedCount;
        private Integer clipsPlayedCount;
        private Integer videoClipsPlayedCount;
        private Integer audioClipsPlayedCount;
        private Integer workflowRunCount;
        private Integer messagesReadFromAppsCount;
        private Integer actionsTakenWithAppsCount;
        private Integer automationsExecutedWithAppsCount;
    }
}
