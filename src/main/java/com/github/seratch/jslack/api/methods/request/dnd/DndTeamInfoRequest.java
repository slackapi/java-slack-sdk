package com.github.seratch.jslack.api.methods.request.dnd;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DndTeamInfoRequest {

    private String token;
    private List<String> users;
}