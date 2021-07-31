package com.slack.api.bolt.service.builtin;

import com.slack.api.bolt.service.OpenIDConnectNonceService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NullOpenIDConnectNonceService implements OpenIDConnectNonceService {

    public NullOpenIDConnectNonceService() {
    }

    @Override
    public String generateNewNonceValue() {
        return null;
    }

    @Override
    public void addNewNonceToDatastore(String Nonce) throws Exception {
        // noop
    }

    @Override
    public boolean isAvailableInDatabase(String Nonce) {
        return false;
    }

    @Override
    public void deleteNonceFromDatastore(String Nonce) throws Exception {
        // noop
    }
}