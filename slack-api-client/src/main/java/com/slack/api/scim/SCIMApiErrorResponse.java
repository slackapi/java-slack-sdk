package com.slack.api.scim;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SCIMApiErrorResponse implements SCIMApiResponse {

    @SerializedName("Errors")
    private Errors errors;

    @Data
    public static class Errors {
        private String description;
        private Integer code;
    }

}