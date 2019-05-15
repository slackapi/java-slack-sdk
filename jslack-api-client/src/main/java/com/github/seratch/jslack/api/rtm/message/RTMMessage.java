package com.github.seratch.jslack.api.rtm.message;

import com.github.seratch.jslack.common.json.GsonFactory;

public interface RTMMessage {

    default String toJSONString() {
        return GsonFactory.createSnakeCase().toJson(this);
    }
}
