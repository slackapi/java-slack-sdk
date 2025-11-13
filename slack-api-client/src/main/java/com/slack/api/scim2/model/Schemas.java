package com.slack.api.scim2.model;

public class Schemas {

    private Schemas() {
    }

    public static final String SCHEMA_CORE_USER = "urn:ietf:params:scim:schemas:core:2.0:User";
    public static final String SCHEMA_EXTENSION_ENTERPRISE_USER = "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User";
    public static final String SCHEMA_EXTENSION_SLACK_GUEST_USER = "urn:ietf:params:scim:schemas:extension:slack:guest:2.0:User";
    public static final String SCHEMA_EXTENSION_SLACK_PROFILE_USER = "urn:ietf:params:scim:schemas:extension:slack:profile:2.0:User";

    public static final String SCHEMA_CORE_GROUP = "urn:ietf:params:scim:schemas:core:2.0:Group";

    public static final String SCHEMA_API_MESSAGES_ERROR = "urn:ietf:params:scim:api:messages:2.0:Error";
    public static final String SCHEMA_API_MESSAGES_PATCH_OP = "urn:ietf:params:scim:api:messages:2.0:PatchOp";
}
