package com.github.seratch.jslack.app_backend.outgoing_webhooks.response;

import com.github.seratch.jslack.api.model.Attachment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WebhookResponse {
    private String text;
    private List<Attachment> attachments;
}
