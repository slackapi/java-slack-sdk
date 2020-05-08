package com.slack.api.model.event;

import java.io.Serializable;

public interface Event extends Serializable {

    String getType();
    default String getSubtype() {
        return "";
    }
}
