package test_locally.api.methods;

import com.slack.api.RequestConfigurator;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.impl.FilesUploadV2Helper;
import com.slack.api.methods.request.files.FilesCompleteUploadExternalRequest;
import com.slack.api.methods.request.files.FilesUploadV2Request;
import com.slack.api.methods.response.files.FilesCompleteUploadExternalResponse;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.util.http.SlackHttpClient;
import okhttp3.OkHttpClient;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilesUploadV2HelperTest {

    @Test
    @SuppressWarnings("unchecked")
    public void completeUploadsPropagatesBlocks() throws Exception {
        // GIVEN
        MethodsClient client = mock(MethodsClient.class);
        when(client.getSlackHttpClient()).thenReturn(new SlackHttpClient(new OkHttpClient()));

        FilesCompleteUploadExternalResponse response = new FilesCompleteUploadExternalResponse();
        response.setOk(true);
        response.setFiles(Collections.emptyList());

        ArgumentCaptor<RequestConfigurator<FilesCompleteUploadExternalRequest.FilesCompleteUploadExternalRequestBuilder>> captor =
                ArgumentCaptor.forClass(RequestConfigurator.class);
        when(client.filesCompleteUploadExternal(captor.capture())).thenReturn(response);

        List<LayoutBlock> blocks = Collections.<LayoutBlock>singletonList(SectionBlock.builder().build());
        FilesUploadV2Request request = FilesUploadV2Request.builder()
                .blocks(blocks)
                .blocksAsString("[]")
                .build();

        // WHEN
        new FilesUploadV2Helper(client).completeUploads(request, Collections.emptyList());

        // THEN
        FilesCompleteUploadExternalRequest completeRequest = captor.getValue()
                .configure(FilesCompleteUploadExternalRequest.builder())
                .build();
        assertThat(completeRequest.getBlocks(), is(blocks));
        assertThat(completeRequest.getBlocksAsString(), is("[]"));
    }
}
