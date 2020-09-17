package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.GroupTopicMessagePayload;
import com.slack.api.model.event.GroupTopicMessageEvent;

public abstract class GroupTopicMessageHandler  extends EventHandler<GroupTopicMessagePayload> {

	@Override
	public String getEventType() {
		return GroupTopicMessageEvent.TYPE_NAME;
	}

	@Override
	public String getEventSubtype() {
		return GroupTopicMessageEvent.SUBTYPE_NAME;
	}
}
