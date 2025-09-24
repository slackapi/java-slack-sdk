package com.slack.api.audit.response;

import com.slack.api.audit.AuditApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SchemasResponse implements AuditApiResponse {
    private transient String rawBody;

    @Deprecated // when an error is returned, it will be an exception instead
    private boolean ok = true;
    @Deprecated // when an error is returned, it will be an exception instead
    private String warning;
    @Deprecated // when an error is returned, it will be an exception instead
    private String error;
    @Deprecated // when an error is returned, it will be an exception instead
    private String needed;
    @Deprecated // when an error is returned, it will be an exception instead
    private String provided;

    private List<Schema> schemas;

    @Data
    public static class Schema {
        private String type;
        private Workspace workspace;
        private Enterprise enterprise;
        private User user;
        private Usergroup usergroup;
        private Role role;
        private AccountTypeRole accountTypeRole;
        private File file;
        private Channel channel;
        private App app;
        private Message message;
        private Workflow workflow;
        private Barrier barrier;
        private Canvas canvas;
        private WorkflowV2 workflowV2;
        private Template template;
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
    public static class Usergroup {
        private String id;
        private String name;
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
        private String appId;
        private String name;
        private String isWorkflowApp;
        private String isDistributed;
        private String isDirectoryApproved;
        private String scopes;
        private String distributionType;
        private String userIds;
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

    @Data
    public static class Role {
        private String id;
        private String name;
        private String type;
    }

    @Data
    public static class AccountTypeRole {
        private String id;
        private String name;
    }

    @Data
    public static class Canvas {
        private String id;
        private String filetype;
        private String title;
    }

    @Data
    public static class WorkflowV2 {
        private String id;
        private String appId;
        private String dateUpdated;
        private String callbackId;
        private String name;
        private String updatedBy;
        private String stepConfiguration;
        private String templateId;
        private String suspiciousKeywords;
        private String triggerName;
        private String triggerChannels;
        private String triggerConfiguration;
    }

    @Data
    public static class Template {
        private String id;
        private String templateName;
    }
}
