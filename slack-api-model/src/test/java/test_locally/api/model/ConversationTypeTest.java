package test_locally.api.model;

import com.slack.api.model.ConversationType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConversationTypeTest {

    @Test
    public void valueOf() {
        assertEquals(ConversationType.valueOf("IM"), ConversationType.IM);
        assertEquals(ConversationType.valueOf("IM").value(), "im");
    }

    @Test
    public void value() {
        assertEquals(ConversationType.PUBLIC_CHANNEL.value(), "public_channel");
        assertEquals(ConversationType.PRIVATE_CHANNEL.value(), "private_channel");
        assertEquals(ConversationType.IM.value(), "im");
        assertEquals(ConversationType.MPIM.value(), "mpim");
    }
}
