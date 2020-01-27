package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.BlockSuggestionContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.BlockSuggestionRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface BlockSuggestionHandler extends Handler<BlockSuggestionContext, BlockSuggestionRequest, Response> {
}
