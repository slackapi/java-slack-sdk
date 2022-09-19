package com.slack.api.bolt.micronaut.handlers;

import com.slack.api.bolt.handler.builtin.BlockActionHandler;

import java.util.regex.Pattern;

public interface MicronautBlockActionHandler extends BlockActionHandler {

    default Pattern getActionIdPattern() {
        return Pattern.compile("^" + Pattern.quote(getActionId()) + "$");
    }

    String getActionId();

}
