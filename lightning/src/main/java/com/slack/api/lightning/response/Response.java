package com.slack.api.lightning.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.slack.api.lightning.util.JsonOps.toJsonString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    @Builder.Default
    private Integer statusCode = 200;
    @Builder.Default
    private String contentType = "plain/text";
    @Builder.Default
    private Map<String, List<String>> headers = new HashMap<>();
    private String body;

    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    public static Response ok() {
        return Response.builder().statusCode(200).build();
    }

    public static Response ok(Object body) {
        if (body instanceof String) {
            return Response.builder().statusCode(200).body((String) body).build();
        } else {
            String json = toJsonString(body);
            return Response.builder()
                    .statusCode(200)
                    .contentType(CONTENT_TYPE_APPLICATION_JSON)
                    .body(json)
                    .build();
        }
    }

    public static Response error(Integer statusCode) {
        return Response.builder().statusCode(statusCode).build();
    }

    public static Response json(Integer statusCode, Object body) {
        return Response.builder()
                .statusCode(statusCode)
                .contentType(CONTENT_TYPE_APPLICATION_JSON)
                .body(toJsonString(body))
                .build();
    }

}
