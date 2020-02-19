package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.BlockSuggestionContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.BlockSuggestionRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface BlockSuggestionHandler extends Handler<BlockSuggestionContext, BlockSuggestionRequest, Response> {
}
