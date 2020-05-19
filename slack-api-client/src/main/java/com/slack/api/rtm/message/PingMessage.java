package com.slack.api.rtm.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PingMessage implements RTMMessage {

	public static final String TYPE_NAME = "ping";

	private Long id;
	private final String type = TYPE_NAME;
	private Instant time;
}
