package com.slack.api.lightning.service.builtin;

import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.response.Response;
import com.slack.api.lightning.service.OAuthStateService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class CookieOAuthStateService implements OAuthStateService {

    public static final long EXPIRATION_IN_SECONDS = 10 * 60; // default 10 min
    public static final String DEFAULT_COOKIE_NAME = "slack-app-oauth-state";

    private final Request request;
    private final Response response;
    private final String cookieName;

    public CookieOAuthStateService(Request request, Response response) {
        this(request, response, DEFAULT_COOKIE_NAME);
    }

    public CookieOAuthStateService(Request request, Response response, String cookieName) {
        this.request = request;
        this.response = response;
        this.cookieName = cookieName;
    }

    @Override
    public String issueNewState() throws Exception {
        String newState = UUID.randomUUID().toString();
        String value = cookieName + "=" + newState + "; Secure; HttpOnly; Path=/; Max-Age=" + EXPIRATION_IN_SECONDS;
        response.getHeaders().put("Set-Cookie", Arrays.asList(value));
        return newState;
    }

    @Override
    public boolean isValid(String state) {
        String cookieHeader = request.getHeaders().getFirstValue("Cookie");
        if (cookieHeader != null) {
            String[] elements = cookieHeader.split(cookieName + "=");
            if (elements != null && elements.length == 2) {
                String cookieValue = elements[1].split(";")[0];
                if (cookieValue != null) {
                    return cookieValue.equals(state);
                }
            }
        } else {
            log.debug("Failed to fetch {} cookie value for state: {}", cookieName, state);
        }
        return false;
    }

    @Override
    public void consume(String state) throws Exception {
        List<String> setCookieHeaderValues = response.getHeaders().get("Set-Cookie");
        if (setCookieHeaderValues == null) {
            setCookieHeaderValues = new ArrayList<>();
        }
        setCookieHeaderValues.add(cookieName + "=deleted; Secure; HttpOnly; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT");
        response.getHeaders().put("Set-Cookie", setCookieHeaderValues);
    }

}