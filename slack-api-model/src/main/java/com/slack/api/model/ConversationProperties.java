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
}
