package com.github.seratch.jslack.api.scim.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class User {

    private List<String> schemas = Arrays.asList(Schemas.SCHEMA_CORE, Schemas.SCHEMA_EXTENSION_ENTERPRISE);

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

    @SerializedName(Schemas.SCHEMA_EXTENSION_ENTERPRISE)
    private Extension extension;

    private List<Group> groups;

    @Data
    public static class Meta {
        private String created;
        private String location;
    }

    @Data
    public static class Name {
        private String givenName;
        private String familyName;
        private String honorificPrefix;
    }

    @Data
    public static class Email {
        private String value;
        private String type;
        private Boolean primary;
    }

    @Data
    public static class Address {
        private String streetAddress;
        private String locality;
        private String region;
        private String postalCode;
        private String country;
        private Boolean primary;
    }

    @Data
    public static class PhoneNumber {
        private String value;
        private String type;
        private Boolean primary;
    }

    @Data
    public static class Photo {
        private String value;
        private String type;
    }

    @Data
    public static class Role {
        private String value;
        private String type;
        private Boolean primary;
    }

    @Data
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

}
