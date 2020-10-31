package com.slack.api.audit.response;

import com.slack.api.audit.AuditApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SchemasResponse implements AuditApiResponse {
    private transient String rawBody;

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Schema> schemas;

    @Data
    public static class Schema {
        private String type;
        private Workspace workspace;
        private Enterprise enterprise;
        private User user;
        private File file;
        private Channel channel;
        private App app;
        private Message message;
        private Workflow workflow;
        private Barrier barrier;
    }

    @Data
    public static class Workspace {
        private String id;
        private String name;
        private String domain;
    }

    @Data
    public static class Enterprise {
        private String id;
        private String name;
        private String domain;
    }

    @Data
    public static class User {
        private String id;
        private String name;
        private String email;
        private String team;
    }

    @Data
    public static class File {
        private String id;
        private String name;
        private String filetype;
        private String title;
    }

    @Data
    public static class Channel {
        private String id;
        private String name;
        private String privacy;
        private String isShared;
        private String isOrgShared;
        private String teamsSharedWith;
    }

    @Data
    public static class App {
        private String id;
        private String name;
        private String isWorkflowApp;
        private String isDistributed;
        private String isDirectoryApproved;
        private String scopes;
    }

    @Data
    public static class Message {
        private String team;
        private String channel;
        private String timestamp;
    }

    @Data
    public static class Workflow {
        private String id;
        private String name;
    }

    @Data
    public static class Barrier {
        private String id;
        private String primaryUsergroup;
        private String barrieredFromUsergroup;
    }

}
