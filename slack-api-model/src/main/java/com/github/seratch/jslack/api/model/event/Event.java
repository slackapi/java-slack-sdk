package com.github.seratch.jslack.api.model.event;

import java.io.Serializable;

public interface Event extends Serializable {

    String getType();

    default String getSubtype() {
        return null;
    }

}
