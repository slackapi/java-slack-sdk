package test_locally.app_backend.events;

import com.github.seratch.jslack.app_backend.events.EventsDispatcher;
import com.github.seratch.jslack.app_backend.events.EventsDispatcherFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventsDispatcherFactoryTest {

    @Test
    public void getInstance() {
        EventsDispatcher dispatcher = EventsDispatcherFactory.getInstance();
        assertThat(dispatcher, is(notNullValue()));
    }
}
