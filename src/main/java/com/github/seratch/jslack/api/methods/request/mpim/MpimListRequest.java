package com.github.seratch.jslack.api.methods.request.mpim;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpimListRequest {

    private String token;
}