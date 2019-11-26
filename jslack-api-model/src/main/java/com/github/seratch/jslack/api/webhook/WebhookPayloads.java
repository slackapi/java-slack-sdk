package com.github.seratch.jslack.api.webhook;

import com.github.seratch.jslack.api.model.ModelConfigurator;

public class WebhookPayloads {

    private WebhookPayloads() {}

    public static Payload payload(ModelConfigurator<Payload.PayloadBuilder> configurator) {
        return configurator.configure(Payload.builder()).build();
    }

}
