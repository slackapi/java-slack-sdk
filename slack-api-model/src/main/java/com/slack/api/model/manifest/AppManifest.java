package com.slack.api.model.manifest;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;
import java.util.Map;

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
    // Automation platform: remote functions
    private Map<String, Function> functions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        private Integer majorVersion;
        private Integer minorVersion;
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
        private String hermesAppType;
        private String functionRuntime;
        // TODO: incoming_webhooks
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
        private Boolean tokenManagementEnabled; // run-on-slack
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Scopes {
        private List<String> bot;
        private List<String> user;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Function {
        private String title;
        private String description;
        private Map<String, ParameterProperty> inputParameters;
        private Map<String, ParameterProperty> outputParameters;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParameterProperty {
        // TODO: this type definition does not cover all the available options yet
        private String type; // "string", "slack#/types/interactivity" etc.
        private String name;
        @SerializedName("is_required")
        private boolean required;
        private String description; // optional
        private String title; // optional
        private String hint; // optional
        @SerializedName("minLength")
        private Integer minLength; // type: string
        @SerializedName("maxLength")
        private Integer maxLength; // type: string
        private Integer minimum; // type: number
        private Integer maximum; // type: number
    }
}
