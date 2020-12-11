package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageChannelTopicPayload;
import com.slack.api.model.event.MessageChannelTopicEvent;

public abstract class MessageChannelTopicHandler extends EventHandler<MessageChannelTopicPayload> {

	@Override
	public String getEventType() {
		return MessageChannelTopicEvent.TYPE_NAME;
	}

	@Override
	public String getEventSubtype() {
		return MessageChannelTopicEvent.SUBTYPE_NAME;
	}
}
