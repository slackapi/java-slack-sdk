package com.github.seratch.jslack.api.methods.request.dnd;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DndEndDndRequest {

    private String token;
}