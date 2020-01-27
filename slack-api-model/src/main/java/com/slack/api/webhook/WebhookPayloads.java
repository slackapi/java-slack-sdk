package com.slack.api.webhook;

import com.slack.api.model.ModelConfigurator;

public class WebhookPayloads {

    private WebhookPayloads() {
    }

    public static Payload payload(ModelConfigurator<Payload.PayloadBuilder> configurator) {
        return configurator.configure(Payload.builder()).build();
    }

}
