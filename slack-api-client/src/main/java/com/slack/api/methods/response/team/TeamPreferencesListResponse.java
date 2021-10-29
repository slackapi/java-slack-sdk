package com.slack.api.methods.response.team;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TeamPreferencesListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private Integer msgEditWindowMins;
    private Boolean allowMessageDeletion;
    private Boolean displayRealNames;
    private String disableFileUploads; // "allow_all"
    private String whoCanPostGeneral; // "ra"
}