package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.context.builtin.BlockSuggestionContext;
import com.github.seratch.jslack.lightning.handler.Handler;
import com.github.seratch.jslack.lightning.request.builtin.BlockSuggestionRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface BlockSuggestionHandler extends Handler<BlockSuggestionContext, BlockSuggestionRequest, Response> {
}
