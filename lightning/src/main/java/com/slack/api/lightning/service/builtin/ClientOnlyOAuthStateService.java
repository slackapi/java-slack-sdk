package com.slack.api.lightning.service.builtin;

import com.slack.api.lightning.service.OAuthStateService;
import lombok.extern.slf4j.Slf4j;

/**
 * A fairly simple OAuthStateService implementation. This one doesn't offer server-side datastore.
 */
@Slf4j
public class ClientOnlyOAuthStateService implements OAuthStateService {

    public ClientOnlyOAuthStateService() {
    }

    @Override
    public void addNewStateToDatastore(String state) throws Exception {
        // noop
    }

    @Override
    public boolean isAvailableInDatabase(String state) {
        return true; // this implementation does nothing, so that the returned value is always true
    }

    @Override
    public void deleteStateFromDatastore(String state) throws Exception {
        // noop
    }

}