package com.slack.api.bolt.micronaut.handlers;

import com.slack.api.bolt.handler.builtin.WorkflowStepSaveHandler;

import java.util.regex.Pattern;

public interface MicronautWorkflowStepSaveHandler extends WorkflowStepSaveHandler {

    default Pattern getCallbackIdPattern() {
        return Pattern.compile("^" + Pattern.quote(getCallbackId()) + "$");
    }

    String getCallbackId();

}
