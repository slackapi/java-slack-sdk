package com.github.seratch.jslack.api.methods.request.team;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamBillableInfoRequest {

    private String token;
    private String user;
}