package com.github.seratch.jslack.app_backend.dialogs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * see https://api.slack.com/dialogs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogSubmissionErrorResponse {

    private List<Error> errors;

}
