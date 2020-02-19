package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface SlashCommandHandler extends Handler<SlashCommandContext, SlashCommandRequest, Response> {
}
