package com.github.seratch.jslack.api.rtm;

import com.github.seratch.jslack.api.model.event.Event;

public interface RTMEventsDispatcher {

    void register(RTMEventHandler<? extends Event> handler);

    void deregister(RTMEventHandler<? extends Event> handler);

    void dispatch(String json);

    RTMMessageHandler toMessageHandler();

}
