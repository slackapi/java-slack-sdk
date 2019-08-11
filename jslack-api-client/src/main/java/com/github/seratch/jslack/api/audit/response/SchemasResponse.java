package com.github.seratch.jslack.api.audit.response;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class SchemasResponse implements SlackApiResponse {
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
        private String isDistributed;
        private String isDirectoryApproved;
        private String scopes;
    }
}
