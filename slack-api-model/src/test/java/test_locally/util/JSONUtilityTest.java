package test_locally.util;

import com.google.gson.*;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.OverflowMenuElement;
import com.slack.api.util.json.GsonBlockElementFactory;
import com.slack.api.util.json.GsonLayoutBlockFactory;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.lang.reflect.Type;

import static com.slack.api.model.block.element.BlockElements.overflowMenu;
import static org.junit.Assert.assertNotNull;

public class JSONUtilityTest {

    final Gson gson = GsonFactory.createSnakeCase();

    class GsonContextImpl implements JsonSerializationContext, JsonDeserializationContext {
        @Override public JsonElement serialize(Object src) {
            return gson.toJsonTree(src);
        }
        @Override public JsonElement serialize(Object src, Type typeOfSrc) {
            return gson.toJsonTree(src, typeOfSrc);
        }
        @SuppressWarnings("unchecked")
        @Override public <R> R deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
            return (R) gson.fromJson(json, typeOfT);
        }
    }

    @Test
    public void testGsonBlockElementFactory() {
        GsonBlockElementFactory f = new GsonBlockElementFactory();
        OverflowMenuElement overflowMenu = overflowMenu(r -> r.actionId("action"));
        GsonContextImpl context = new GsonContextImpl();

        JsonElement json = f.serialize(overflowMenu, OverflowMenuElement.class, context);
        assertNotNull(json);

        BlockElement elem = f.deserialize(json, OverflowMenuElement.class, context);
        assertNotNull(elem);
    }

    @Test
    public void testGsonLayoutBlockFactory() {
        GsonLayoutBlockFactory f = new GsonLayoutBlockFactory();
        GsonContextImpl context = new GsonContextImpl();

        DividerBlock block = DividerBlock.builder().build();
        JsonElement json = f.serialize(block, DividerBlock.class, context);
        assertNotNull(json);

        LayoutBlock b = f.deserialize(json, DividerBlock.class, context);
        assertNotNull(b);
    }
}
