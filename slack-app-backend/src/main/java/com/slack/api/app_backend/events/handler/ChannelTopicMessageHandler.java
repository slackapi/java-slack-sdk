package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ChannelTopicMessagePayload;
import com.slack.api.model.event.ChannelTopicMessageEvent;

public abstract class ChannelTopicMessageHandler  extends EventHandler<ChannelTopicMessagePayload> {

	@Override
	public String getEventType() {
		return ChannelTopicMessageEvent.TYPE_NAME;
	}

	@Override
	public String getEventSubtype() {
		return ChannelTopicMessageEvent.SUBTYPE_NAME;
	}
}
