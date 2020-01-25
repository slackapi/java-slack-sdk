package com.github.seratch.jslack.lightning.service;

public interface OAuthStateService {

    String issueNewState() throws Exception;

    boolean isValid(String state);

    void consume(String state) throws Exception;

}
