package com.slack.api.methods.response.im;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Im;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
public class ImListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<Im> ims;
    private ResponseMetadata responseMetadata;
}
