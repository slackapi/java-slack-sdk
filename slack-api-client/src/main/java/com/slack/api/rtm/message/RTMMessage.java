package com.slack.api.rtm.message;

import com.slack.api.util.json.GsonFactory;

public interface RTMMessage {

    default String toJSONString() {
        return GsonFactory.createSnakeCase().toJson(this);
    }
}
