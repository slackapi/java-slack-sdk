package com.slack.api.bolt.micronaut.handlers;

import com.slack.api.bolt.handler.builtin.BlockSuggestionHandler;

import java.util.regex.Pattern;

public interface MicronautBlockSuggestionHandler extends BlockSuggestionHandler {

    default Pattern getActionIdPattern() {
        return Pattern.compile("^" + Pattern.quote(getActionId()) + "$");
    }

    String getActionId();

}
