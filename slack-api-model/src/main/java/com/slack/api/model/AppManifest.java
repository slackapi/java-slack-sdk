package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppManifest {

    @SerializedName("_metadata")
    private Metadata metadata;
    private DisplayInformation displayInformation;
    private Settings settings;
    private Features features;
    private OAuthConfig oauthConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        private String majorVersion;
        private String minorVersion;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DisplayInformation {
        private String name;
        private String longDescription;
        private String description;
        private String backgroundColor;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Settings {
        private String description;
        private String longDescription;
        private String backgroundColor;
        private EventSubscriptions eventSubscriptions;
        private Interactivity interactivity;
        private List<String> allowedIpAddressRanges;
        private Boolean orgDeployEnabled;
        private Boolean socketModeEnabled;
        private Boolean tokenRotationEnabled;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Interactivity {
        private Boolean isEnabled;
        private String requestUrl;
        private String messageMenuOptionsUrl;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventSubscriptions {
        private List<String> botEvents;
        private List<String> userEvents;
        private String requestUrl;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Features {
        private AppHome appHome;
        private BotUser botUser;
        private List<Shortcut> shortcuts;
        private List<SlashCommand> slashCommands;
        private List<String> unfurlDomains;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppHome {
        private Boolean homeTabEnabled;
        private Boolean messagesTabEnabled;
        private Boolean messagesTabReadOnlyEnabled;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BotUser {
        private String displayName;
        private Boolean alwaysOnline;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Shortcut {
        private String type; // message / global
        private String callbackId;
        private String name;
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SlashCommand {
        private String command;
        private String description;
        private String usageHint;
        private String url;
        private Boolean shouldEscape;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OAuthConfig {
        private Scopes scopes;
        private List<String> redirectUrls;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Scopes {
        private List<String> bot;
        private List<String> user;
    }
}
