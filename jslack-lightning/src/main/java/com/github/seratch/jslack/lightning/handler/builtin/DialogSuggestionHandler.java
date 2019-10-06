package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.context.builtin.DialogSuggestionContext;
import com.github.seratch.jslack.lightning.handler.Handler;
import com.github.seratch.jslack.lightning.request.builtin.DialogSuggestionRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface DialogSuggestionHandler extends Handler<DialogSuggestionContext, DialogSuggestionRequest, Response> {
}
