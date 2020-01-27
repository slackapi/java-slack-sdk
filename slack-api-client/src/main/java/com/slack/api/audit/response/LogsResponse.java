package com.slack.api.audit.response;

import com.google.gson.annotations.SerializedName;
import com.slack.api.audit.AuditApiResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class LogsResponse implements AuditApiResponse {
    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private ResponseMetadata responseMetadata;

    private List<Entry> entries;

    @Data
    public static class Entry {
        private String id;
        private Integer dateCreate;
        private String action;
        private Actor actor;
        private Entity entity;
        private Context context;
        private Details details;
    }

    @Data
    public static class Actor {
        private String type;
        private User user;
    }

    @Data
    public static class User {
        private String id;
        private String name;
        private String email;
        private String team;
    }

    @Data
    public static class Entity {
        private String type;
        private App app;
        private User user;
        private Usergroup usergroup;
        private Workspace workspace;
        private Enterprise enterprise;
        private File file;
        private Channel channel;
    }

    @Data
    public static class App {
        private String id;
        private String name;
        @SerializedName("is_distributed")
        private Boolean distributed;
        @SerializedName("is_directory_approved")
        private Boolean directoryApproved;
        private List<String> scopes;
    }

    @Data
    public static class Usergroup {
        private String id;
        private String name;
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
        @SerializedName("is_shared")
        private Boolean shared;
        @SerializedName("is_org_shared")
        private Boolean orgShared;
        private List<String> teamsSharedWith;
    }

    @Data
    public static class Context {
        private Location location;
        private String ua;
        private String ipAddress;
    }

    @Data
    public static class Location {
        private String type;
        private String id;
        private String name;
        private String domain;
    }

    @Data
    public static class Details {
        @SerializedName("is_internal_integration")
        private Boolean internalIntegration;
        private String appOwnerId;
        private List<String> newScopes;
        private List<String> previousScopes;
        private String type;
        private Inviter inviter;
        private String newValue;
        private String previousValue;
        private Kicker kicker;
        private String installerUserId;
        private Boolean appPreviouslyApproved;
        private List<String> oldScopes;
        private String name;
        private String botId;
        private List<Permission> permissions;
    }

    @Data
    public static class Inviter {
        private String type;
        private User user;
        private String id;
        private String name;
        private String email;
    }

    @Data
    public static class Kicker {
        private String id;
        private String name;
        private String email;
    }

    @Data
    public static class Permission {
        private Resource resource;
        private List<String> scopes;
    }

    @Data
    public static class Resource {
        private String type;
        private Grant grant;
    }

    @Data
    public static class Grant {
        private String type;
        private String resourceId;
        private WildCard wildcard;
    }

    @Data
    public static class WildCard {
        private String type;
    }

}
