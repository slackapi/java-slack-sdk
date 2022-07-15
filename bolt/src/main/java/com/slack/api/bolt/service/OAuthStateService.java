package com.slack.api.bolt.service;

import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;

import java.util.*;

/**
 * Manages state parameters for Slack OAuth flow.
 *
 * @see <a href="https://api.slack.com/docs/oauth">Slack OAuth</a>
 */
public interface OAuthStateService extends Service {

    long DEFAULT_EXPIRATION_IN_SECONDS = 10 * 60; // default 10 min

    /**
     * Returns the cookie name used for browser sessions.
     */
    default String getSessionCookieName() {
        // the default one with raw value
        return "slack-app-oauth-state";
    }

    /**
     * Returns the state parameter value from a given query string if it exists.
     */
    default String extractStateFromQueryString(Request request) {
        Map<String, List<String>> queryString = request.getQueryString();
        for (Object key : queryString.keySet()) {
            if (key.equals("state")) {
                List<String> values = queryString.get(key);
                if (values != null && values.size() > 0) {
                    return values.get(0); // use the first param
                }
            }
        }
        return null;
    }

    // -----------------------------------------------------------

    /**
     * Issues a new state parameter and set it in both the browser session and the system's server-side datastore.
     */
    default String issueNewState(Request request, Response response) throws Exception {
        String newState = generateNewStateValue();
        // 1) set the value in the user's browser cookie
        String headerValue = getSessionCookieName() + "=" + generateSessionCookieValue(request, newState);
        headerValue += "; Secure; HttpOnly; Path=/; Max-Age=" + getExpirationInSeconds();
        response.getHeaders().put("Set-Cookie", Arrays.asList(headerValue));
        addNewStateToDatastore(newState);
        return newState;
    }

    /**
     * Generates a new unique state parameter value.
     */
    default String generateNewStateValue() {
        return UUID.randomUUID().toString();
    }

    /**
     * Returns the time period of expiration for state values in seconds.
     */
    default long getExpirationInSeconds() {
        return DEFAULT_EXPIRATION_IN_SECONDS;
    }

    /**
     * Generates a cookie value. The default behavior is to use the raw value as-is.
     * If you'd like to go with another representation, have your own class and override this method.
     */
    default String generateSessionCookieValue(Request request, String state) throws Exception {
        // the default simple implementation doesn't do anything with request
        return state;
    }

    /**
     * Adds a newly generated state value to the server-side datastore.
     */
    void addNewStateToDatastore(String state) throws Exception;

    // -----------------------------------------------------------

    /**
     * Verifies the given state parameter and returns true if it's valid.
     * This method doesn't have any side effects, so that you can call this method multiple times.
     */
    default boolean isValid(Request request) {
        // 0) the state parameter is available in the query string
        String givenState = extractStateFromQueryString(request);
        if (givenState == null || givenState.trim().isEmpty()) {
            return false;
        }
        // 1) the value is the same with the one in the cookie-based session
        String stateInSession = extractStateFromSession(request);
        if (stateInSession == null || stateInSession.trim().isEmpty()) {
            return false;
        }
        if (!stateInSession.equals(givenState)) {
            return false;
        }
        // 2) the value is valid on the server-side
        return isAvailableInDatabase(givenState);
    }

    /**
     * Returns an underlying state value in a cookie-based session if it exists.
     */
    default String extractStateFromSession(Request request) {
        List<String> cookieHeaders = request.getHeaders().getMultipleValues("Cookie");
        if (cookieHeaders == null) {
            return null;
        }
        for (String header : cookieHeaders) {
            String[] cookies = header.split(";");
            for (String cookie : cookies) {
                if (cookie.trim().startsWith(getSessionCookieName() + "=")) {
                    return cookie.trim().split("=")[1];
                }
            }
        }
        return null;
    }

    /**
     * Verifies the state value is available in the datastore and returns true if it's valid.
     */
    boolean isAvailableInDatabase(String state);

    // -----------------------------------------------------------

    /**
     * Deletes the state value from the server-side datastore and set a response header to delete the one in session.
     */
    default void consume(Request request, Response response) throws Exception {
        String givenState = extractStateFromQueryString(request);
        // delete the cookie
        List<String> setCookieHeaderValues = response.getHeaders().get("Set-Cookie");
        if (setCookieHeaderValues == null) {
            setCookieHeaderValues = new ArrayList<>();
        }
        setCookieHeaderValues.add(getSessionCookieName() + "=deleted; Secure; HttpOnly; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT");
        response.getHeaders().put("Set-Cookie", setCookieHeaderValues);
        // delete the row from datastore
        deleteStateFromDatastore(givenState);
    }

    /**
     * Deletes a given state value from the server-side datastore.
     */
    void deleteStateFromDatastore(String state) throws Exception;

}
