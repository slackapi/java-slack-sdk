package com.github.seratch.jslack.api.rtm.message;

import com.github.seratch.jslack.common.json.GsonFactory;

public class RTMMessageToJSONString {

    private RTMMessageToJSONString() {
    }

    public static String toJSON(RTMMessage message) {
        return GsonFactory.createSnakeCase().toJson(message);
    }

}
