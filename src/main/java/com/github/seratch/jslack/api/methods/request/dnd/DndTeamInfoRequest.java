package com.github.seratch.jslack.api.methods.request.dnd;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

@Data
@Builder
public class DndTeamInfoRequest implements SlackApiRequest {

    private String token;
    private List<String> users;
}