package com.slack.api.scim2;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SCIM2ApiErrorResponse implements SCIM2ApiResponse {

    private List<String> schemas;
    private String detail;
    private Integer status;

    @SerializedName("Errors")
    private Errors errors;

    @Data
    public static class Errors {
        private String description;
        private Integer code;
    }

}