package com.slack.api.methods.response.admin.analytics.members;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminAnalyticsMembersMetadataResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<Member> members;
    private Integer totalCount;
    private ResponseMetadata responseMetadata;

    @Data
    public static class Member {
        private String userId;
        private String teamId;
        private String enterpriseId;
        private Integer dateCreated;
        private Integer dateDeleted;
        private String username;
        private String realName;
        private String displayName;
        private String userTitle;
        private String email;
        private String alternateEmail;
        private Map<String, String> workspaces;
        private String department;
        private String costCenter;
        private String division;
        private String primaryPhone;
        private String mobilePhone;
        private String role;
        private String userType;
        private String employeeNumber;
        private String locale;
        private String honorificPrefix;
        private String managerId;
        private String organization;
        private String preferredLanguage;
        private String streetAddress;
        private String country;
        private String city;
        private String zipCode;
    }
}
