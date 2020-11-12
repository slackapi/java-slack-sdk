package com.slack.api.methods.response.users;

import com.slack.api.model.User;

public class UsersLookupByEmailResponse extends com.slack.api.methods.response.channels.UsersLookupByEmailResponse {
    public UsersLookupByEmailResponse() {
        super();
    }

    @Override
    public boolean isOk() {
        return super.isOk();
    }

    @Override
    public String getWarning() {
        return super.getWarning();
    }

    @Override
    public String getError() {
        return super.getError();
    }

    @Override
    public String getNeeded() {
        return super.getNeeded();
    }

    @Override
    public String getProvided() {
        return super.getProvided();
    }

    @Override
    public User getUser() {
        return super.getUser();
    }

    @Override
    public void setOk(boolean ok) {
        super.setOk(ok);
    }

    @Override
    public void setWarning(String warning) {
        super.setWarning(warning);
    }

    @Override
    public void setError(String error) {
        super.setError(error);
    }

    @Override
    public void setNeeded(String needed) {
        super.setNeeded(needed);
    }

    @Override
    public void setProvided(String provided) {
        super.setProvided(provided);
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected boolean canEqual(Object other) {
        return super.canEqual(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
