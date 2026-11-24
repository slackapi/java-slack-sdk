package test_locally.api.methods;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.response.dnd.DndEndSnoozeResponse;
import com.slack.api.methods.response.dnd.DndInfoResponse;
import com.slack.api.methods.response.dnd.DndSetSnoozeResponse;
import com.slack.api.methods.response.dnd.DndTeamInfoResponse;
import com.slack.api.util.json.GsonFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class DndTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        assertThat(slack.methods(ValidToken).dndEndDnd(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).dndEndSnooze(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).dndInfo(r -> r.user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).dndSetSnooze(r -> r.numMinutes(10))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).dndTeamInfo(r -> r.users(Arrays.asList("U123")))
                .isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).dndEndDnd(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).dndEndSnooze(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).dndInfo(r -> r.user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).dndSetSnooze(r -> r.numMinutes(10))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).dndTeamInfo(r -> r.users(Arrays.asList("U123")))
                .get().isOk(), is(true));
    }

    // https://github.com/slackapi/java-slack-sdk/issues/1719
    // Slack returns 9999999999 when no DND end is scheduled, which overflows Integer
    @Test
    public void timestampsLargerThanIntegerMaxValue() {
        Gson gson = GsonFactory.createSnakeCase();
        long ts = 9999999999L;

        DndInfoResponse info = gson.fromJson(
                "{\"ok\":true,\"next_dnd_start_ts\":" + ts + ",\"next_dnd_end_ts\":" + ts + ",\"snooze_endtime\":" + ts + "}",
                DndInfoResponse.class);
        assertThat(info.getNextDndStartTs(), is(ts));
        assertThat(info.getNextDndEndTs(), is(ts));
        assertThat(info.getSnoozeEndtime(), is(ts));

        DndEndSnoozeResponse endSnooze = gson.fromJson(
                "{\"ok\":true,\"next_dnd_start_ts\":" + ts + ",\"next_dnd_end_ts\":" + ts + "}",
                DndEndSnoozeResponse.class);
        assertThat(endSnooze.getNextDndStartTs(), is(ts));
        assertThat(endSnooze.getNextDndEndTs(), is(ts));

        DndSetSnoozeResponse setSnooze = gson.fromJson(
                "{\"ok\":true,\"snooze_endtime\":" + ts + "}",
                DndSetSnoozeResponse.class);
        assertThat(setSnooze.getSnoozeEndtime(), is(ts));

        DndTeamInfoResponse teamInfo = gson.fromJson(
                "{\"ok\":true,\"users\":{\"U123\":{\"dnd_enabled\":true,\"next_dnd_start_ts\":" + ts + ",\"next_dnd_end_ts\":" + ts + "}}}",
                DndTeamInfoResponse.class);
        assertThat(teamInfo.getUsers().get("U123").getNextDndStartTs(), is(ts));
        assertThat(teamInfo.getUsers().get("U123").getNextDndEndTs(), is(ts));
    }

}
