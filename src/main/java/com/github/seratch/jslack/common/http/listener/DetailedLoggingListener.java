package com.github.seratch.jslack.common.http.listener;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okio.Buffer;

import java.io.IOException;

@Slf4j
public class DetailedLoggingListener extends HttpResponseListener {

    @Override
    public void accept(State state) {
        if (log.isDebugEnabled()) {
            Response response = state.getResponse();
            String body = state.getParsedResponseBody();

            Buffer requestBody = new Buffer();
            try {
                response.request().body().writeTo(requestBody);
            } catch (IOException e) {
                log.error("Failed to read the request body because {}", e.getMessage(), e);
            }

            String textRequestBody = null;
            try {
                textRequestBody = requestBody.buffer().readUtf8();
            } catch (Exception e) {
                log.debug("Failed to read request body because {}, error: {}", e.getMessage(), e.getClass().getCanonicalName());
            }

            Long contentLength = null;
            try {
                contentLength = response.request().body().contentLength();
            } catch (IOException e) {
                log.error("Failed to read the content length because {}", e.getMessage(), e);
            }

            log.debug("\n[Request URL]\n{} {}\n" +
                            "[Specified Request Headers]\n{}" +
                            "[Request Body]\n{}\n\n" +
                            "Content-Type: {}\n" +
                            "Content Length: {}\n" +
                            "\n" +
                            "[Response Status]\n{} {}\n" +
                            "[Response Headers]\n{}" +
                            "[Response Body]\n{}\n",
                    response.request().method(),
                    response.request().url(),
                    response.request().headers(),
                    textRequestBody,
                    response.request().body().contentType(),
                    contentLength,
                    response.code(),
                    response.message(),
                    response.headers(),
                    body);
        }
    }
}
