package com.slack.api.scim2.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
public class User {

    private List<String> schemas = Arrays.asList(
            Schemas.SCHEMA_CORE_USER,
            Schemas.SCHEMA_EXTENSION_ENTERPRISE_USER,
            Schemas.SCHEMA_EXTENSION_SLACK_GUEST_USER,
            Schemas.SCHEMA_EXTENSION_SLACK_PROFILE_USER
    );

    private String id;
    private String externalId;
    private Meta meta;
    private String userName;
    private String nickName;
    private Name name;
    private String displayName;
    private String profileUrl;
    private List<Email> emails;
    private List<Address> addresses;
    private List<PhoneNumber> phoneNumbers;
    private List<Photo> photos;
    private List<Role> roles;
    private String userType;
    private String title;
    private String preferredLanguage;
    private String locale;
    private String timezone;
    private Boolean active;
    // The password attribute is never returned
    // but can be used to set the initial password for a user
    // if the team is not using an identity manager.
    private String password;

    @SerializedName(Schemas.SCHEMA_EXTENSION_ENTERPRISE_USER)
    private Extension extension;
    @SerializedName(Schemas.SCHEMA_EXTENSION_SLACK_GUEST_USER)
    private SlackGuest slackGuest;
    @SerializedName(Schemas.SCHEMA_EXTENSION_SLACK_PROFILE_USER)
    private SlackProfile slackProfile;

    private List<Group> groups;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        private String created;
        private String location;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Name {
        private String givenName;
        private String familyName;
        private String honorificPrefix;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Email {
        private String value;
        private String type;
        private Boolean primary;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        private String streetAddress;
        private String locality;
        private String region;
        private String postalCode;
        private String country;
        private Boolean primary;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneNumber {
        private String value;
        private String type;
        private Boolean primary;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Photo {
        private String value;
        private String type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Role {
        private String value;
        private String type;
        private Boolean primary;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Extension {
        private String employeeNumber;
        private String costCenter;
        private String organization;
        private String division;
        private String department;
        private Manager manager;

        @Data
        public static class Manager {
            private String managerId;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Group {
        private String value;
        private String display;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SlackGuest {
        public static class Types {
            private Types() {
            }

            public static final String MULTI = "multi";
        }

        /**
         * This value is mandatory.
         * possible values: "multi"
         */
        private String type;
        /**
         * This value is optional.
         * possible values: ISO 8601 date time string (e.g., "2020-11-30T23:59:59Z")
         */
        private String expiration;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SlackProfile {
        /**
         * Identifies date and time the user started within the organization. Used to create and display profile Celebrations.
         * The accepted format is ISO 8601. (e.g., "2020-10-31T23:59:59Z")
         */
        private String startDate;
    }
}
