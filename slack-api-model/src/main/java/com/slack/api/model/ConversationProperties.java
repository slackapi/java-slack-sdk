package com.slack.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationProperties {

    private Canvas canvas;
    private PostingRestrictedTo postingRestrictedTo;
    private ThreadsRestrictedTo threadsRestrictedTo;
    private Boolean huddlesRestricted;
    private Boolean atHereRestricted;
    private Boolean atChannelRestricted;
    private List<Tab> tabs;
    private List<Tabz> tabz;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostingRestrictedTo {
        private List<String> type;
        private List<String> user;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThreadsRestrictedTo {
        private List<String> type;
        private List<String> user;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Canvas {
        private String fileId;
        private String quipThreadId;
        private Boolean isEmpty;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tab {
        private String id;
        private String label;
        private String type;
        private TabData data;
        private Boolean isDisabled;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tabz {
        private String id;
        private String label;
        private String type;
        private TabData data;
        private Boolean isDisabled;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TabData {
        private String fileId;
        private String viewId;
        private String accessLevel;
        private String sharedTs;
        private String recordRelatedListId;
        private String salesforceListViewId;
        private String listsChannelTabDefaultRefineViewId;
        private String objectType;
        private String folderBookmarkId;
        private String botUserId;
        private String appId;
        private Boolean muteEditUpdates;
    }
}
