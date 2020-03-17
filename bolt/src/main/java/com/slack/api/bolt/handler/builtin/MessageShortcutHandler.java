package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.MessageShortcutContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.MessageShortcutRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface MessageShortcutHandler extends Handler<MessageShortcutContext, MessageShortcutRequest, Response> {
}
