package com.github.seratch.jslack.api.rtm;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RTMEventsDispatcherImplTest {

    @Test
    public void eventDetection() {
        String type = RTMEventsDispatcherImpl.detectEventType("{\"type\": \"hello\"}");
        assertThat(type, is("hello"));
    }
}