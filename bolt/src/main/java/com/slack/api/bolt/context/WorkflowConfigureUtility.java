package com.slack.api.bolt.context;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.block.LayoutBlock;

import java.io.IOException;
import java.util.List;

import static com.slack.api.model.view.Views.view;

/**
 * @deprecated Use new custom steps: https://api.slack.com/automation/functions/custom-bolt
 */
@Deprecated
public interface WorkflowConfigureUtility {

    String getTriggerId();
    String getCallbackId();

    MethodsClient client();

    default ViewsOpenResponse configure(
            List<LayoutBlock> blocks
    ) throws IOException, SlackApiException {
        return configure(blocks, null, null, null);
    }

    default ViewsOpenResponse configure(
            List<LayoutBlock> blocks,
            String privateMetadata
    ) throws IOException, SlackApiException {
        return configure(blocks, privateMetadata, null, null);
    }

    default ViewsOpenResponse configure(
            List<LayoutBlock> blocks,
            String privateMetadata,
            String externalId
    ) throws IOException, SlackApiException {
        return configure(blocks, privateMetadata, externalId, null);
    }

    default ViewsOpenResponse configure(
            List<LayoutBlock> blocks,
            String privateMetadata,
            String externalId,
            Boolean submitDisabled
    ) throws IOException, SlackApiException {
        ViewsOpenResponse response = client().viewsOpen(r -> r
                .triggerId(getTriggerId())
                .view(view(v -> v
                        .type("workflow_step")
                        .callbackId(getCallbackId())
                        .externalId(externalId)
                        .privateMetadata(privateMetadata)
                        .submitDisabled(submitDisabled)
                        .blocks(blocks)
                )));
        return response;
    }

}
