package com.slack.api.bolt.micronaut.handlers;

import com.slack.api.bolt.handler.builtin.AttachmentActionHandler;

import java.util.regex.Pattern;

public interface MicronautAttachmentActionHandler extends AttachmentActionHandler {

    default Pattern getCallbackIdPattern() {
        return Pattern.compile("^" + Pattern.quote(getCallbackId()) + "$");
    }

    String getCallbackId();

}
