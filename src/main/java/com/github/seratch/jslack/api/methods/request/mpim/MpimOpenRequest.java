package com.github.seratch.jslack.api.methods.request.mpim;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MpimOpenRequest {

    private String token;
    private List<String> users;
}