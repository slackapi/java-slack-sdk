package com.slack.api.scim2.request;

import com.slack.api.scim2.model.PatchOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupsPatchOperation {
    private PatchOperation op;
    private String path;

    private List<Member> memberValues;

    @Data
    @Builder
    public static class Member {
        private String value;
    }

    @Data
    @Builder
    public static class Serializable {
        private PatchOperation op;
        private String path;
        private Object value;
    }

    public Serializable toSerializable() {
        return Serializable.builder()
                .op(this.getOp())
                .path(this.getPath())
                .value(this.extractValue())
                .build();
    }

    private Object extractValue() {
        if (this.getMemberValues() != null) {
            return this.getMemberValues();
        } else {
            // "remove" operation does not require any value
            return null;
        }
    }
}
