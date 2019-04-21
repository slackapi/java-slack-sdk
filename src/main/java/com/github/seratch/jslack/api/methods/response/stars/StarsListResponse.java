package com.github.seratch.jslack.api.methods.response.stars;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.Paging;
import lombok.Data;

import java.util.List;

@Data
public class StarsListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Message> items;
    private Paging paging;
}