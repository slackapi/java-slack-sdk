package com.slack.api.model.event;

import com.slack.api.model.view.View;
import lombok.Data;

/**
 * This app event notifies your app when a user has entered into the App Home space—that's the place
 * where a user exchanges DMs with your app.
 * <p>
 * Your Slack app must have a bot user configured and installed to use this event.
 * <p>
 * Use the app_home_opened event to begin a friendly onboarding flow from your app,
 * a whimsical welcome message, or a deep-dive into a detailed dialog.
 * Since the app_home_opened event is only sent to your app when a user has already clicked on your app,
 * you can rest assured that your attentions are welcome.
 * <p>
 * Note: app_home_opened events are sent each time a user enters into the App Home space.
 * Verify that this is the first interaction between a user and your app before triggering your onboarding flow.
 * <p>
 * app_home_opened events are just like other message events sent over the Events API, but their type indicates app_home_opened.
 * <p>
 * https://docs.slack.dev/reference/events/app_home_opened
 */
@Data
public class AppHomeOpenedEvent implements Event {

    public static final String TYPE_NAME = "app_home_opened";

    private final String type = TYPE_NAME;
    private String user;
    private String channel;
    private String tab; // home, messages
    private String eventTs;
    private View view;
}