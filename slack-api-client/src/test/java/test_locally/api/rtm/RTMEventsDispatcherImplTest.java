package test_locally.api.rtm;

import com.slack.api.rtm.RTMEventsDispatcherImpl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RTMEventsDispatcherImplTest {

    @Test
    public void eventDetection() {
        String type = RTMEventsDispatcherImpl.detectEventType("{\"type\": \"hello\"}");
        assertThat(type, is("hello"));
    }
}
