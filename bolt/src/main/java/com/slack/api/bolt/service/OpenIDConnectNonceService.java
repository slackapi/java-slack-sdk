package com.slack.api.bolt.service;

import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;

import java.util.*;

/**
 * Manages nonce parameters for Slack OpenID Connect flow.
 *
 * @see <a href="https://docs.slack.dev/authentication/sign-in-with-slack">Sign in with Slack</a>
 */
public interface OpenIDConnectNonceService extends Service {

    /**
     * Returns the Nonce parameter value from a given query string if it exists.
     */
    default String extractNonceFromQueryString(Request request) {
        Map<String, List<String>> queryString = request.getQueryString();
        for (Object key : queryString.keySet()) {
            if (key.equals("nonce")) {
                List<String> values = queryString.get(key);
                if (values != null && values.size() > 0) {
                    return values.get(0); // use the first param
                }
            }
        }
        return null;
    }

    /**
     * Issues a new Nonce parameter and set it in both the browser session and the system's server-side datastore.
     */
    default String issueNewNonce(Request request, Response response) throws Exception {
        String newNonce = generateNewNonceValue();
        addNewNonceToDatastore(newNonce);
        return newNonce;
    }

    /**
     * Generates a new unique Nonce parameter value.
     */
    String generateNewNonceValue();

    /**
     * Adds a newly generated Nonce value to the server-side datastore.
     */
    void addNewNonceToDatastore(String nonce) throws Exception;

    // -----------------------------------------------------------

    /**
     * Verifies the given Nonce parameter and returns true if it's valid.
     * This method doesn't have any side effects, so that you can call this method multiple times.
     */
    default boolean isValid(Request request) {
        String givenNonce = extractNonceFromQueryString(request);
        if (givenNonce == null || givenNonce.trim().isEmpty()) {
            return false;
        }
        return isAvailableInDatabase(givenNonce);
    }

    /**
     * Verifies the Nonce value is available in the datastore and returns true if it's valid.
     */
    boolean isAvailableInDatabase(String nonce);

    // -----------------------------------------------------------

    /**
     * Deletes the Nonce value from the server-side datastore and set a response header to delete the one in session.
     */
    default void consume(Request request, Response response) throws Exception {
        String givenNonce = extractNonceFromQueryString(request);
        deleteNonceFromDatastore(givenNonce);
    }

    /**
     * Deletes a given Nonce value from the server-side datastore.
     */
    void deleteNonceFromDatastore(String nonce) throws Exception;

}
