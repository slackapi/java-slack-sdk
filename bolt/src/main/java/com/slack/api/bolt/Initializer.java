package com.slack.api.bolt;

import java.util.function.Consumer;

/**
 * A function that initializes {@link com.slack.api.bolt.service.Service}s and whatever related to {@link App}.
 */
public interface Initializer extends Consumer<App> {
}
