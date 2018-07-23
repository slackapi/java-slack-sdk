package com.github.seratch.jslack.api.methods.response.im;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Im;
import com.github.seratch.jslack.api.model.ResponseMetadata;

import lombok.Data;

import java.util.List;

@Data
public class ImListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private List<Im> ims;
    private ResponseMetadata responseMetadata;
}
