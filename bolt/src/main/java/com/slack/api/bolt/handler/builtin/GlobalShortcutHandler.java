package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.GlobalShortcutContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.GlobalShortcutRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface GlobalShortcutHandler extends Handler<GlobalShortcutContext, GlobalShortcutRequest, Response> {
}
