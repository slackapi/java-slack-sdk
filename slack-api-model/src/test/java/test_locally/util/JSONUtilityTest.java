package test_locally.util;

import com.google.gson.*;
import com.slack.api.model.predicate.FieldPredicate;
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
import com.slack.api.model.annotation.Required;
import com.slack.api.util.json.*;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.image;
import static com.slack.api.model.block.element.BlockElements.overflowMenu;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

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

    @Test
    public void testRequiredPropertyDetectionAdapterFactory_basicCase_failureCases() {
        Gson gson = GsonFactory.getBuilder(true, true)
                .registerTypeAdapterFactory(new RequiredPropertyDetectionAdapterFactory()).create();

        // Serialization
        TestClassWithRequiredBasic instance = TestClassWithRequiredBasic.builder().build();
        assertThrows(JsonParseException.class, () -> gson.toJson(instance));

        // Deserialization
        String json = "{\"name\": \"Hello\"}";
        assertThrows(JsonParseException.class, () -> gson.fromJson(json, TestClassWithRequiredBasic.class));
    }

    @Test
    public void testRequiredPropertyDetectionAdapterFactory_basicCase_happyPath() {
        Gson gson = GsonFactory.getBuilder(true, true).registerTypeAdapterFactory(new RequiredPropertyDetectionAdapterFactory()).create();
        TestClassWithRequiredBasic instanceNoName = TestClassWithRequiredBasic.builder().id(1).build();
        TestClassWithRequiredBasic instanceWithName = TestClassWithRequiredBasic.builder().id(1).name("Hello").build();

        // Serialization
        assertThat(gson.toJson(instanceNoName), is("{\"id\":1}"));
        assertThat(gson.toJson(instanceWithName), is("{\"id\":1,\"name\":\"Hello\"}"));

        // Deserialization
        String json = "{\"id\": 1}";
        TestClassWithRequiredBasic instance = gson.fromJson(json, TestClassWithRequiredBasic.class);
        assertThat(instance.getId(), is(1));
        assertNull(instance.getName());

        json = "{\"id\": 1, \"name\": \"Hello\"}";
        instance = gson.fromJson(json, TestClassWithRequiredBasic.class);
        assertThat(instance.getId(), is(1));
        assertThat(instance.getName(), is("Hello"));
    }

    @Test
    public void testRequiredPropertyDetectionAdapterFactory_advancedCase_failureCases() {
        Gson gson = GsonFactory.getBuilder(true, true).registerTypeAdapterFactory(new RequiredPropertyDetectionAdapterFactory()).create();

        // Serialization
        JsonParseException e = assertThrows(JsonParseException.class, () -> gson.toJson(TestClassWithRequiredAdvanced.builder().build()));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'id' failed validation in TestClassWithRequiredAdvanced using predicate IntegerGreaterThanZero"));

        e = assertThrows(JsonParseException.class, () -> gson.toJson(TestClassWithRequiredAdvanced.builder().id(1).build()));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'name' failed validation in TestClassWithRequiredAdvanced using predicate NonEmptyString"));

        e = assertThrows(JsonParseException.class, () -> gson.toJson(TestClassWithRequiredAdvanced.builder().id(1).name("").build()));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'name' failed validation in TestClassWithRequiredAdvanced using predicate NonEmptyString"));

        e = assertThrows(JsonParseException.class, () -> gson.toJson(TestClassWithRequiredAdvanced.builder().id(1).name("Hello").build()));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'users' failed validation in TestClassWithRequiredAdvanced using predicate NonEmptyCollection"));

        e = assertThrows(JsonParseException.class, () -> gson.toJson(TestClassWithRequiredAdvanced.builder().id(1).name("Hello").users(List.of("user1")).build()));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'myBool' failed validation in TestClassWithRequiredAdvanced using predicate isNotNullFieldPredicate"));

        // Deserialization
        e = assertThrows(JsonParseException.class, () -> gson.fromJson("{\"id\": 0}", TestClassWithRequiredAdvanced.class));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'id' failed validation in TestClassWithRequiredAdvanced using predicate IntegerGreaterThanZero"));

        e = assertThrows(JsonParseException.class, () -> gson.fromJson("{\"id\": 1}", TestClassWithRequiredAdvanced.class));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'name' failed validation in TestClassWithRequiredAdvanced using predicate NonEmptyString"));

        e = assertThrows(JsonParseException.class, () -> gson.fromJson("{\"id\": 1, \"name\": ''}", TestClassWithRequiredAdvanced.class));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'name' failed validation in TestClassWithRequiredAdvanced using predicate NonEmptyString"));

        e = assertThrows(JsonParseException.class, () -> gson.fromJson("{\"id\":1,\"name\":\"test\",\"users\":[]}", TestClassWithRequiredAdvanced.class));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'users' failed validation in TestClassWithRequiredAdvanced using predicate NonEmptyCollection"));

        e = assertThrows(JsonParseException.class, () -> gson.fromJson("{\"id\":1,\"name\":\"test\",\"users\":[\"hello\"]}", TestClassWithRequiredAdvanced.class));
        assertThat(e.getMessage(), equalToIgnoringCase("Required field 'myBool' failed validation in TestClassWithRequiredAdvanced using predicate isNotNullFieldPredicate"));
    }

    @Test
    public void testRequiredPropertyDetectionAdapterFactory_advancedCase_happyPath() {
        Gson gson = GsonFactory.getBuilder(true, true).registerTypeAdapterFactory(new RequiredPropertyDetectionAdapterFactory()).create();
        TestClassWithRequiredAdvanced instance = TestClassWithRequiredAdvanced.builder()
                .id(1)
                .name("test")
                .users(List.of("testUser"))
                .myBool(true)
                .build();

        // Serialization
        assertThat(gson.toJson(instance), is("{\"id\":1,\"name\":\"test\",\"users\":[\"testUser\"],\"my_bool\":true}"));

        // Deserialization
        String json = "{\"id\":1,\"name\":\"test\",\"users\":[\"testUser\"],\"my_bool\":true}";
        instance = gson.fromJson(json, TestClassWithRequiredAdvanced.class);
        assertThat(instance.getId(), is(1));
        assertThat(instance.getName(), is("test"));
        assertThat(instance.getUsers().get(0), is("testUser"));
        assertThat(instance.getMyBool(), is(true));
        assertNull(instance.getCanBeNull());
    }

    @Data
    @Builder
    private static class TestClassWithRequiredBasic {
        @Required private Integer id;
        private String name;
    }

    @Data
    @Builder
    private static class TestClassWithRequiredAdvanced {
        @Required(validator = IntegerGreaterThanZero.class)
        private int id;
        @Required(validator = NonEmptyString.class)
        private String name;
        @Required(validator = NonEmptyCollection.class)
        private List<String> users;
        @Required
        Boolean myBool;
        private String canBeNull;

        public static class IntegerGreaterThanZero implements FieldPredicate {
            @Override
            public boolean validate(Object obj) {
                return obj instanceof Integer && (int)obj > 0;
            }
        }

        public static class NonEmptyString implements FieldPredicate {
            @Override
            public boolean validate(Object obj) {
                return obj instanceof String && !((String) obj).isEmpty();
            }
        }

        public static class NonEmptyCollection implements FieldPredicate {
            @Override
            public boolean validate(Object obj) {
                Predicate<Collection<?>> isNotEmpty = collection -> !collection.isEmpty();
                return obj instanceof Collection && isNotEmpty.test((Collection<?>) obj);
            }
        }
    }
}
