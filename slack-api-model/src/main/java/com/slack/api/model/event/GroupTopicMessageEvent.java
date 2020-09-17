package com.slack.api.model.event;

import lombok.Data;

/**
 * https://api.slack.com/events/message/group_topic
 */
@Data
public class GroupTopicMessageEvent implements Event {

	public static final String TYPE_NAME = "message";
	public static final String SUBTYPE_NAME = "group_topic";

	private final String type = TYPE_NAME;
	private final String subtype = SUBTYPE_NAME;

	private String user;
	private String team;
	private String channel;

	private String topic;
	private String text;

	private String ts;
	private String eventTs;
}
