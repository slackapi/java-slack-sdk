package com.slack.api.scim.model;

public class Schemas {

    private Schemas() {
    }

    public static final String SCHEMA_CORE = "urn:scim:schemas:core:1.0";
    public static final String SCHEMA_EXTENSION_ENTERPRISE = "urn:scim:schemas:extension:enterprise:1.0";
    public static final String SCHEMA_EXTENSION_SLACK_GUEST = "urn:scim:schemas:extension:slack:guest:1.0";
    public static final String SCHEMA_EXTENSION_SLACK_PROFILE = "urn:scim:schemas:extension:slack:profile:1.0";
}
