package test_locally.util;

import com.google.gson.*;
import com.slack.api.model.block.ContextBlockElement;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.TextObject;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.ImageElement;
import com.slack.api.model.block.element.OverflowMenuElement;
import com.slack.api.model.event.FunctionExecutedEvent;
import com.slack.api.util.json.*;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.lang.reflect.Type;
import java.util.Arrays;

import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.image;
import static com.slack.api.model.block.element.BlockElements.overflowMenu;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class JSONUtilityTest {

    class GsonContextImpl implements JsonSerializationContext, JsonDeserializationContext {
        @Override
        public JsonElement serialize(Object src) {
            return gson.toJsonTree(src);
        }

        @Override
        public JsonElement serialize(Object src, Type typeOfSrc) {
            return gson.toJsonTree(src, typeOfSrc);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R> R deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
            return (R) gson.fromJson(json, typeOfT);
        }
    }


    final Gson gson = GsonFactory.createSnakeCase();
    final GsonContextImpl context = new GsonContextImpl();

    @Test
    public void testGsonBlockElementFactory() {
        GsonBlockElementFactory f = new GsonBlockElementFactory();
        OverflowMenuElement overflowMenu = overflowMenu(r -> r.actionId("action"));

        JsonElement json = f.serialize(overflowMenu, OverflowMenuElement.class, context);
        assertNotNull(json);

        BlockElement elem = f.deserialize(json, OverflowMenuElement.class, context);
        assertNotNull(elem);
    }

    @Test
    public void testGsonLayoutBlockFactory() {
        GsonLayoutBlockFactory f = new GsonLayoutBlockFactory();

        DividerBlock block = DividerBlock.builder().build();
        JsonElement json = f.serialize(block, DividerBlock.class, context);
        assertNotNull(json);

        LayoutBlock b = f.deserialize(json, DividerBlock.class, context);
        assertNotNull(b);
    }

    @Test
    public void testGsonContextBlockElementFactory_image() {
        GsonContextBlockElementFactory f = new GsonContextBlockElementFactory();

        ImageElement image = image(r -> r.imageUrl("https://www.example.com/image.png"));
        JsonElement json = f.serialize(image, ImageElement.class, context);
        assertNotNull(json);

        ContextBlockElement i = f.deserialize(json, ImageElement.class, context);
        assertNotNull(i);
    }

    @Test
    public void testGsonContextBlockElementFactory_markdownText() {
        GsonContextBlockElementFactory f = new GsonContextBlockElementFactory();

        MarkdownTextObject elm = markdownText(r -> r);
        JsonElement json = f.serialize(elm, MarkdownTextObject.class, context);
        assertNotNull(json);

        ContextBlockElement i = f.deserialize(json, MarkdownTextObject.class, context);
        assertNotNull(i);
    }

    @Test
    public void testGsonContextBlockElementFactory_plainText() {
        GsonContextBlockElementFactory f = new GsonContextBlockElementFactory();

        PlainTextObject elm = plainText(r -> r);
        JsonElement json = f.serialize(elm, PlainTextObject.class, context);
        assertNotNull(json);

        ContextBlockElement i = f.deserialize(json, PlainTextObject.class, context);
        assertNotNull(i);
    }

    @Test
    public void testGsonTextObjectFactory_markdownText() {
        GsonTextObjectFactory f = new GsonTextObjectFactory();

        MarkdownTextObject elm = markdownText(r -> r);
        JsonElement json = f.serialize(elm, MarkdownTextObject.class, context);
        assertNotNull(json);

        TextObject i = f.deserialize(json, MarkdownTextObject.class, context);
        assertNotNull(i);
    }

    @Test
    public void testGsonTextObjectFactory_plainText() {
        GsonTextObjectFactory f = new GsonTextObjectFactory();

        PlainTextObject elm = plainText(r -> r);
        JsonElement json = f.serialize(elm, PlainTextObject.class, context);
        assertNotNull(json);

        TextObject i = f.deserialize(json, PlainTextObject.class, context);
        assertNotNull(i);
    }

    @Test
    public void testGsonFunctionExecutedEventInputValueFactory() {
        GsonFunctionExecutedEventInputValueFactory f = new GsonFunctionExecutedEventInputValueFactory();

        FunctionExecutedEvent.InputValue value = new FunctionExecutedEvent.InputValue();
        value.setStringValue("U111");
        JsonElement json = f.serialize(value, FunctionExecutedEvent.InputValue.class, context);
        assertNotNull(json);
        FunctionExecutedEvent.InputValue parsed = f.deserialize(json, FunctionExecutedEvent.InputValue.class, context);
        assertThat(parsed.asString(), is("U111"));
        assertThat(parsed.asStringArray(), is(Arrays.asList("U111")));

        value.setStringValue(null);
        value.setStringValues(Arrays.asList("C111", "C222"));
        json = f.serialize(value, FunctionExecutedEvent.InputValue.class, context);
        assertNotNull(json);
        parsed = f.deserialize(json, FunctionExecutedEvent.InputValue.class, context);
        assertThat(parsed.asStringArray(), is(Arrays.asList("C111", "C222")));
    }
}
