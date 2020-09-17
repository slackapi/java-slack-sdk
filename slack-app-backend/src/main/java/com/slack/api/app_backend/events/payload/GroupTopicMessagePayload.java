package com.slack.api.app_backend.events.payload;

import com.slack.api.model.event.GroupTopicMessageEvent;
import lombok.Data;

import java.util.List;

@Data
public class GroupTopicMessagePayload implements EventsApiPayload<GroupTopicMessageEvent> {

	private String token;
	private String enterpriseId;
	private String teamId;
	private String apiAppId;
	private String type;
	private List<String> authedUsers;
	private List<String> authedTeams;
	private String eventId;
	private Integer eventTime;

	private GroupTopicMessageEvent event;
}
