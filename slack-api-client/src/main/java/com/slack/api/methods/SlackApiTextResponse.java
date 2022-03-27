package com.slack.api.methods;

/**
 * Most of the Slack APIs return text data. This interface defines the common properties of those.
 */
public interface SlackApiTextResponse extends SlackApiResponse {

    boolean isOk();

    void setOk(boolean isOk);

    String getWarning();

    void setWarning(String warning);

    String getError();

    void setError(String error);

    // only when having a permission error

    String getNeeded();

    void setNeeded(String needed);

    String getProvided();

    void setProvided(String provided);

}
