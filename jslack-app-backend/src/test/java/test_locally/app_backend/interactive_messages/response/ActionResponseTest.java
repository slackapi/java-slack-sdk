package test_locally.app_backend.interactive_messages.response;

import com.github.seratch.jslack.app_backend.interactive_messages.response.ActionResponse;
import com.github.seratch.jslack.common.json.GsonFactory;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ActionResponseTest {

    @Test
    public void test() {
        ActionResponse response = new ActionResponse();
        response.setText("something to say");
        response.setAttachments(Collections.emptyList());
        response.setBlocks(Collections.emptyList());
        response.setDeleteOriginal(false);
        response.setReplaceOriginal(true);

        assertThat(response.getText(), is("something to say"));

        String json = GsonFactory.createSnakeCase().toJson(response);
        assertThat(json, is("{" +
                "\"text\":\"something to say\"," +
                "\"replace_original\":true," +
                "\"delete_original\":false," +
                "\"attachments\":[]," +
                "\"blocks\":[]" +
                "}"));
    }

}
