package com.github.seratch.jslack.api.scim.request;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.scim.model.Schemas;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class GroupsPatchRequest implements SlackApiRequest {
    private String token;
    private String id;
    private GroupOperation group;

    @Data
    public static class GroupOperation {
        private List<String> schemas = Arrays.asList(Schemas.SCHEMA_CORE);
        private List<MemberOperation> members;
    }

    @Data
    public static class MemberOperation {
        private String value;
        private String operation;
    }

}
