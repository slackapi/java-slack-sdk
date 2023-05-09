package com.slack.api.scim2.request;

import com.slack.api.scim2.SCIM2ApiRequest;
import com.slack.api.scim2.model.Schemas;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class GroupsPatchRequest implements SCIM2ApiRequest {
    private String token;
    private String id;
    @Builder.Default
    private List<String> schemas = Arrays.asList(Schemas.SCHEMA_API_MESSAGES_PATCH_OP);
    @Builder.Default
    private List<GroupsPatchOperation> operations = new ArrayList<>();

}
