package com.slack.api.model.event;

import lombok.Data;

/**
 * https://api.slack.com/events/message/bot_add
 * (undocumented)
 */
@Data
public class MessageBotAddEvent implements Event {

  public static final String TYPE_NAME = "message";
  public static final String SUBTYPE_NAME = "bot_add";

  private final String type = TYPE_NAME;
  private final String subtype = SUBTYPE_NAME;

  private String botId;
  private String user;

  private String channel;

  private String text;
  private String botLink;

  private String ts;

  private String eventTs;
  private String channelType;
}
