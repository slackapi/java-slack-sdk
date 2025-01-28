package com.slack.api.util.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slack.api.SlackConfig;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.model.Attachment;
import com.slack.api.model.File;
import com.slack.api.model.admin.AppWorkflow;
import com.slack.api.model.block.ContextBlockElement;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.TextObject;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.RichTextElement;
import com.slack.api.model.event.FunctionExecutedEvent;
import com.slack.api.model.event.MessageChangedEvent;

import java.time.Instant;

/**
 * Gson Factory for the entire SDK. This factory enables some Slack-specific settings.
 */
public class GsonFactory {
    private GsonFactory() {
    }

    /**
     * Most of the Slack APIs' key naming is snake-cased.
     */
    public static Gson createSnakeCase() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Instant.class, new JavaTimeInstantFactory())
                .registerTypeAdapter(File.class, new GsonFileFactory())
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory())
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory())
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory())
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory())
                .registerTypeAdapter(RichTextElement.class, new GsonRichTextElementFactory())
                .registerTypeAdapter(FunctionExecutedEvent.InputValue.class, new GsonFunctionExecutedEventInputValueFactory())
                .registerTypeAdapter(Attachment.VideoHtml.class, new GsonMessageAttachmentVideoHtmlFactory())
                .registerTypeAdapter(MessageChangedEvent.PreviousMessage.class, new GsonMessageChangedEventPreviousMessageFactory())
                .registerTypeAdapter(AppWorkflow.StepInputValue.class, new GsonAppWorkflowStepInputValueFactory())
                .registerTypeAdapter(AppWorkflow.StepInputValueElementDefault.class, new GsonAppWorkflowStepInputValueDefaultFactory())
                .registerTypeAdapter(LogsResponse.DetailsChangedValue.class, new GsonAuditLogsDetailsChangedValueFactory())
                .registerTypeAdapter(LogsResponse.UserIDs.class, new GsonAuditLogsDetailsUserIDsFactory())
                .create();
    }

    /**
     * Most of the Slack APIs' key naming is snake-cased.
     */
    public static Gson createSnakeCase(SlackConfig config) {
        boolean failOnUnknownProps = config.isFailOnUnknownProperties();
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Instant.class, new JavaTimeInstantFactory(failOnUnknownProps))
                .registerTypeAdapter(File.class, new GsonFileFactory(failOnUnknownProps))
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory(failOnUnknownProps))
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory(failOnUnknownProps))
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory(failOnUnknownProps))
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory(failOnUnknownProps))
                .registerTypeAdapter(RichTextElement.class, new GsonRichTextElementFactory(failOnUnknownProps))
                .registerTypeAdapter(FunctionExecutedEvent.InputValue.class, new GsonFunctionExecutedEventInputValueFactory())
                .registerTypeAdapter(Attachment.VideoHtml.class, new GsonMessageAttachmentVideoHtmlFactory(failOnUnknownProps))
                .registerTypeAdapter(MessageChangedEvent.PreviousMessage.class, new GsonMessageChangedEventPreviousMessageFactory(failOnUnknownProps))
                .registerTypeAdapter(AppWorkflow.StepInputValue.class, new GsonAppWorkflowStepInputValueFactory(failOnUnknownProps))
                .registerTypeAdapter(AppWorkflow.StepInputValueElementDefault.class, new GsonAppWorkflowStepInputValueDefaultFactory(failOnUnknownProps))
                .registerTypeAdapter(LogsResponse.DetailsChangedValue.class, new GsonAuditLogsDetailsChangedValueFactory(failOnUnknownProps))
                .registerTypeAdapter(LogsResponse.UserIDs.class, new GsonAuditLogsDetailsUserIDsFactory(failOnUnknownProps));
        if (failOnUnknownProps || config.isLibraryMaintainerMode()) {
            gsonBuilder = gsonBuilder.registerTypeAdapterFactory(new UnknownPropertyDetectionAdapterFactory());
        }
        if (config.isPrettyResponseLoggingEnabled()) {
            gsonBuilder = gsonBuilder.setPrettyPrinting();
        }
        return gsonBuilder.create();
    }

    /**
     * Mainly used for SCIM APIs.
     */
    public static Gson createCamelCase(SlackConfig config) {
        boolean failOnUnknownProps = config.isFailOnUnknownProperties();
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new JavaTimeInstantFactory(failOnUnknownProps))
                .registerTypeAdapter(File.class, new GsonFileFactory(failOnUnknownProps))
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory(failOnUnknownProps))
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory(failOnUnknownProps))
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory(failOnUnknownProps))
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory(failOnUnknownProps))
                .registerTypeAdapter(RichTextElement.class, new GsonRichTextElementFactory(failOnUnknownProps))
                .registerTypeAdapter(MessageChangedEvent.PreviousMessage.class, new GsonMessageChangedEventPreviousMessageFactory(failOnUnknownProps))
                .registerTypeAdapter(LogsResponse.DetailsChangedValue.class, new GsonAuditLogsDetailsChangedValueFactory(failOnUnknownProps))
                .registerTypeAdapter(MessageChangedEvent.PreviousMessage.class, new GsonMessageChangedEventPreviousMessageFactory(failOnUnknownProps))
                .registerTypeAdapter(AppWorkflow.StepInputValue.class, new GsonAppWorkflowStepInputValueFactory(failOnUnknownProps))
                .registerTypeAdapter(AppWorkflow.StepInputValueElementDefault.class, new GsonAppWorkflowStepInputValueDefaultFactory(failOnUnknownProps))
                .registerTypeAdapter(LogsResponse.DetailsChangedValue.class, new GsonAuditLogsDetailsChangedValueFactory(failOnUnknownProps))
                .registerTypeAdapter(LogsResponse.UserIDs.class, new GsonAuditLogsDetailsUserIDsFactory(failOnUnknownProps));
        if (failOnUnknownProps || config.isLibraryMaintainerMode()) {
            gsonBuilder = gsonBuilder.registerTypeAdapterFactory(new UnknownPropertyDetectionAdapterFactory());
        }
        if (config.isPrettyResponseLoggingEnabled()) {
            gsonBuilder = gsonBuilder.setPrettyPrinting();
        }
        return gsonBuilder.create();
    }
}
