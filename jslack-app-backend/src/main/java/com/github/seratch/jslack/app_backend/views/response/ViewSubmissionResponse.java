package com.github.seratch.jslack.app_backend.views.response;

import com.github.seratch.jslack.api.model.view.View;
import lombok.Data;

@Data
public class ViewSubmissionResponse {
    private String responseAction; // push, update, errors, (no value to close)
    private View view;
}
