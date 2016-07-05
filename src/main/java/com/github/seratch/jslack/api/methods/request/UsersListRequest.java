package com.github.seratch.jslack.api.methods.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersListRequest {

    private String token;
    private Integer presence;
}