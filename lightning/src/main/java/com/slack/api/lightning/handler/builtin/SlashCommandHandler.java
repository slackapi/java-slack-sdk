package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.SlashCommandContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.SlashCommandRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface SlashCommandHandler extends Handler<SlashCommandContext, SlashCommandRequest, Response> {
}
