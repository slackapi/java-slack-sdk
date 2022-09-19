package com.slack.api.bolt.micronaut.handlers;

import com.slack.api.bolt.handler.builtin.SlashCommandHandler;

import java.util.regex.Pattern;

public interface MicronautSlashCommandHandler extends SlashCommandHandler {

    default Pattern getCommandIdPattern() {
        return Pattern.compile("^" + Pattern.quote(getCommandId()) + "$");
    }

    default String getCommandId() {
        throw new UnsupportedOperationException("Implement either this method or getCommandIdPattern()");
    };

}
