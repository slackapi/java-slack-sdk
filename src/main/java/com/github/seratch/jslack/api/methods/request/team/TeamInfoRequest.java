package com.github.seratch.jslack.api.methods.request.team;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamInfoRequest {

    private String token;
}