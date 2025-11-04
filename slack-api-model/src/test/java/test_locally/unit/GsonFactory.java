package test_locally.unit;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slack.api.model.Attachment;
import com.slack.api.model.File;
import com.slack.api.model.block.ContextBlockElement;
import com.slack.api.model.block.ContextActionsBlockElement;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.TextObject;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.RichTextElement;
import com.slack.api.model.event.FunctionExecutedEvent;
import com.slack.api.model.event.MessageChangedEvent;
import com.slack.api.util.json.*;

public class GsonFactory {
    private GsonFactory() {
    }

    public static Gson createSnakeCase() {
        return createSnakeCase(false, true);
    }

    public static Gson createSnakeCaseWithoutUnknownPropertyDetection(boolean failOnUnknownProperties) {
        return createSnakeCase(failOnUnknownProperties, false);
    }

    public static Gson createSnakeCase(boolean failOnUnknownProperties, boolean unknownPropertyDetection) {
        GsonBuilder builder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(File.class, new GsonFileFactory(failOnUnknownProperties))
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory(failOnUnknownProperties))
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory(failOnUnknownProperties))
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory(failOnUnknownProperties))
                .registerTypeAdapter(ContextActionsBlockElement.class, new GsonContextActionsBlockElementFactory(failOnUnknownProperties))
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory(failOnUnknownProperties))
                .registerTypeAdapter(RichTextElement.class, new GsonRichTextElementFactory(failOnUnknownProperties))
                .registerTypeAdapter(FunctionExecutedEvent.InputValue.class,
                        new GsonFunctionExecutedEventInputValueFactory(failOnUnknownProperties))
                .registerTypeAdapter(Attachment.VideoHtml.class,
                        new GsonMessageAttachmentVideoHtmlFactory(failOnUnknownProperties))
                .registerTypeAdapter(MessageChangedEvent.PreviousMessage.class,
                        new GsonMessageChangedEventPreviousMessageFactory(failOnUnknownProperties));

        if (unknownPropertyDetection) {
            return builder.registerTypeAdapterFactory(new UnknownPropertyDetectionAdapterFactory()).create();
        } else {
            return builder.create();
        }
    }
}
