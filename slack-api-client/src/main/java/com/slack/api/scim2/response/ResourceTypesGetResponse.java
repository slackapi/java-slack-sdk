package com.slack.api.scim2.response;

import com.google.gson.annotations.SerializedName;
import com.slack.api.scim2.SCIM2ApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceTypesGetResponse implements SCIM2ApiResponse {
    private List<String> schemas;
    @SerializedName("Resources")
    private List<Resource> resources;

    @Data
    public static class Resource {
        private List<String> schemas;
        private String id;
        private String name;
        private String endpoint;
        private String description;
        private String schema;
        private Meta meta;
        private List<SchemaExtension> schemaExtensions;
    }

    @Data
    public static class Meta {
        private String location;
        private String resourceType;
    }

    @Data
    public static class SchemaExtension {
        private String schema;
        private boolean required;
    }
}
