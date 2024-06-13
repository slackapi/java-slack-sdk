package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.request.canvases.sections.CanvasesSectionsLookupRequest;
import com.slack.api.model.canvas.CanvasDocumentChange;
import com.slack.api.model.canvas.CanvasDocumentContent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class CanvasesTest {

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
    public void canvases() throws Exception {
        assertThat(slack.methods(ValidToken).canvasesCreate(r -> r.title("test").documentContent(new CanvasDocumentContent())).isOk(), is(true));
        assertThat(slack.methods(ValidToken).canvasesCreate(r -> r.title("test").markdown(">hey")).isOk(), is(true));
        assertThat(slack.methods(ValidToken).canvasesEdit(r -> r.canvasId("F1111").changes(Arrays.asList(
                CanvasDocumentChange.builder().documentContent(new CanvasDocumentContent()).build()
        ))).isOk(), is(true));
        assertThat(slack.methods(ValidToken).canvasesDelete(r -> r.canvasId("F1111")).isOk(), is(true));
        assertThat(slack.methods(ValidToken).canvasesAccessSet(r -> r
                .canvasId("F1111")
                .channelIds(Arrays.asList("C123"))
                .userIds(Arrays.asList("U123"))
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).canvasesAccessDelete(r -> r
                .canvasId("F1111")
                .channelIds(Arrays.asList("C123"))
                .userIds(Arrays.asList("U123"))
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).canvasesSectionsLookup(r -> r
                .canvasId("F1111")
                .criteria(CanvasesSectionsLookupRequest.Criteria.builder().sectionTypes(Arrays.asList("any_header")).containsText("foo").build())
        ).isOk(), is(true));
    }

    @Test
    public void canvases_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).canvasesCreate(r -> r.title("test").documentContent(new CanvasDocumentContent())).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).canvasesCreate(r -> r.title("test").markdown(">hey")).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).canvasesEdit(r -> r.canvasId("F1111").changes(Arrays.asList(
                CanvasDocumentChange.builder().documentContent(new CanvasDocumentContent()).build()
        ))).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).canvasesDelete(r -> r.canvasId("F1111")).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).canvasesAccessSet(r -> r
                .canvasId("F1111")
                .channelIds(Arrays.asList("C123"))
                .userIds(Arrays.asList("U123"))
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).canvasesAccessDelete(r -> r
                .canvasId("F1111")
                .channelIds(Arrays.asList("C123"))
                .userIds(Arrays.asList("U123"))
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).canvasesSectionsLookup(r -> r
                .canvasId("F1111")
                .criteria(CanvasesSectionsLookupRequest.Criteria.builder().sectionTypes(Arrays.asList("any_header")).containsText("foo").build())
        ).get().isOk(), is(true));
    }

}
