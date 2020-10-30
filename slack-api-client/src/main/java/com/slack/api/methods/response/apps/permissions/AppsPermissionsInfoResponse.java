package com.slack.api.methods.response.apps.permissions;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;

@Data
public class AppsPermissionsInfoResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

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