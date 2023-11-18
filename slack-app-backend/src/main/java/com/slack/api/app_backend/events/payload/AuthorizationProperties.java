package com.slack.api.app_backend.events.payload;

import java.util.List;

public interface AuthorizationProperties {
    List<Authorization> getAuthorizations();

    void setAuthorizations(List<Authorization> authorizations);
}
