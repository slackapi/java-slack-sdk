package com.slack.api.bolt.micronaut.handlers;

import com.slack.api.bolt.handler.builtin.DialogSuggestionHandler;

import java.util.regex.Pattern;

public interface MicronautDialogSuggestionHandler extends DialogSuggestionHandler {

    default Pattern getCallbackIdPattern() {
        return Pattern.compile("^" + Pattern.quote(getCallbackId()) + "$");
    }

    default String getCallbackId() {
        throw new UnsupportedOperationException("Implement either this method or getCallbackIdPattern()");
    }

}
