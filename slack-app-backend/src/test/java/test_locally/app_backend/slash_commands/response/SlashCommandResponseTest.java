package test_locally.app_backend.slash_commands.response;

import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SlashCommandResponseTest {

    @Test
    public void test() {
        SlashCommandResponse response = new SlashCommandResponse();
        response.setResponseType("ephemeral");
        assertThat(response.getResponseType(), is("ephemeral"));
    }

}
