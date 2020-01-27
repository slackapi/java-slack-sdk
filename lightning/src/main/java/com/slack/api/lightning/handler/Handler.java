package com.slack.api.lightning.handler;

import com.slack.api.lightning.context.Context;
import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.response.Response;
import com.slack.api.methods.SlackApiException;

import java.io.IOException;

@FunctionalInterface
public interface Handler<
        CTX extends Context,
        REQ extends Request<CTX>,
        RESP extends Response> {

    RESP apply(REQ req, CTX context) throws IOException, SlackApiException;

}
