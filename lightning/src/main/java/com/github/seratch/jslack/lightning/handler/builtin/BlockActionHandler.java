package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.context.builtin.ActionContext;
import com.github.seratch.jslack.lightning.handler.Handler;
import com.github.seratch.jslack.lightning.request.builtin.BlockActionRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface BlockActionHandler extends Handler<ActionContext, BlockActionRequest, Response> {
}
