package com.github.seratch.jslack.api.methods.request.mpim;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

@Data
@Builder
public class MpimOpenRequest implements SlackApiRequest {

    private String token;
    private List<String> users;
}