package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.DialogSuggestionContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.DialogSuggestionRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface DialogSuggestionHandler extends Handler<DialogSuggestionContext, DialogSuggestionRequest, Response> {
}
