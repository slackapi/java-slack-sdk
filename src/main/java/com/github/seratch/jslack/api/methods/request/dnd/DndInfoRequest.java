package com.github.seratch.jslack.api.methods.request.dnd;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DndInfoRequest {

    private String token;
    private String user;
}