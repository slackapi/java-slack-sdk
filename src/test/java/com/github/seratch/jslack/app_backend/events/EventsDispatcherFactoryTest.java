package com.github.seratch.jslack.app_backend.events;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class EventsDispatcherFactoryTest {

    @Test
    public void getInstance() {
        EventsDispatcher dispatcher = EventsDispatcherFactory.getInstance();
        assertThat(dispatcher, is(notNullValue()));
    }
}