package com.slack.api.bolt.handler;

import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;

import java.io.IOException;

/**
 * Slack App Handler.
 * @param <CTX> context
 * @param <REQ> request
 * @param <RESP> response
 */
@FunctionalInterface
public interface Handler<
        CTX extends Context,
        REQ extends Request<CTX>,
        RESP extends Response> {

    /**
     * A function returns a response corresponding to the given request and its context.
     * @param req request
     * @param context context
     * @return response
     * @throws IOException
     * @throws SlackApiException
     */
    RESP apply(REQ req, CTX context) throws IOException, SlackApiException;

}
