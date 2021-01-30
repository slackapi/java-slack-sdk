package com.slack.api.rate_limits.queue;

import java.util.UUID;

public class MessageIdGeneratorUUIDImpl implements MessageIdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }

}
