package com.slack.api.app_backend.events.payload;

public interface CommonEventProperties {
    String getType();

    void setType(String type);

    String getEventId();

    void setEventId(String eventId);

    Integer getEventTime();

    void setEventTime(Integer eventTime);

    String getEventContext();

    void setEventContext(String eventContext);

    boolean isExtSharedChannel();

    void setExtSharedChannel(boolean isExtSharedChannel);
}
