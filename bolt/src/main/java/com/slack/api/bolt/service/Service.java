package com.slack.api.bolt.service;

import com.slack.api.bolt.Initializer;

/**
 * Service interface
 */
public interface Service {

    /**
     * Returns the initializer for this service. If the service has time-consuming initialization steps,
     * putting those into this function would be a good way to avoid timeout errors
     * for the first incoming request (in other words, to avoid cold-start problems).
     */
    default Initializer initializer() {
        return (app) -> {
        };
    }

}
