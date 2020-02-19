package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.DialogSuggestionContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.DialogSuggestionRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface DialogSuggestionHandler extends Handler<DialogSuggestionContext, DialogSuggestionRequest, Response> {
}
