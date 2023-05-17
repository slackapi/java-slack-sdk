package com.slack.api.scim2.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Group {

    private List<String> schemas = Arrays.asList(Schemas.SCHEMA_CORE_GROUP);

    private String id;
    private String displayName;
    private Meta meta;
    private List<Member> members;

    @Data
    public static class Meta {
        private String created;
        private String location;
    }

    @Data
    public static class Member {
        private String value;
        private String display;
    }
}
