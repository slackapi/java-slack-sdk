package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.context.builtin.DefaultContext;
import com.github.seratch.jslack.lightning.handler.Handler;
import com.github.seratch.jslack.lightning.request.builtin.ViewClosedRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface ViewClosedHandler extends Handler<DefaultContext, ViewClosedRequest, Response> {
}
