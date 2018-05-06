package com.github.seratch.jslack.api.methods.response.apps.permissions;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class AppsPermissionsInfoResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private Info info;

    @Data
    public static class Info {
        private Permissions team;
        private Permissions channel;
        private Permissions group;
        private Permissions mpim;
        private Permissions im;
        private Permissions appHome;

        @Data
        public static class Permissions {
            private List<String> scopes;
            private Resources resources;

            @Data
            public static class Resources {
                private List<String> ids;
                private boolean wildcard;
                private List<String> excludedIds;
            }
        }
    }

}