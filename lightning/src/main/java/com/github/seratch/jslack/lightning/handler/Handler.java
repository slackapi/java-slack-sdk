package com.github.seratch.jslack.lightning.handler;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.lightning.context.Context;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.response.Response;

import java.io.IOException;

@FunctionalInterface
public interface Handler<
        CTX extends Context,
        REQ extends Request<CTX>,
        RESP extends Response> {

    RESP apply(REQ req, CTX context) throws IOException, SlackApiException;

}
