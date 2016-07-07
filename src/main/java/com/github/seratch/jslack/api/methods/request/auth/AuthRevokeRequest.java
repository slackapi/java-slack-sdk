package com.github.seratch.jslack.api.methods.request.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRevokeRequest {

    private String token;
    private String test;
}