package com.slack.api.lightning.service;

public interface OAuthStateService {

    String issueNewState() throws Exception;

    boolean isValid(String state);

    void consume(String state) throws Exception;

}
