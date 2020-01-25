package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.context.builtin.SlashCommandContext;
import com.github.seratch.jslack.lightning.handler.Handler;
import com.github.seratch.jslack.lightning.request.builtin.SlashCommandRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface SlashCommandHandler extends Handler<SlashCommandContext, SlashCommandRequest, Response> {
}
