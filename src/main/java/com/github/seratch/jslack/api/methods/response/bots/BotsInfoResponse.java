package com.github.seratch.jslack.api.methods.response.bots;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class BotsInfoResponse implements SlackApiResponse {

    @Data
    public static class Bot {
        private String id;
        private String appId;
        private String userId;
        private String name;
        private boolean deleted;
        private Integer updated;
        private Icons icons;

        @Data
        public static class Icons {
            private String image36;
            private String image48;
            private String image72;
        }
    }

    private boolean ok;
    private String warning;
    private String error;

    private Bot bot;
}