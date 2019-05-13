package test_locally.unit;

import com.github.seratch.jslack.api.model.block.ContextBlockElement;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.github.seratch.jslack.api.model.block.composition.TextObject;
import com.github.seratch.jslack.api.model.block.element.BlockElement;
import com.github.seratch.jslack.common.json.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
    private GsonFactory() {
    }

    public static Gson createSnakeCase() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(LayoutBlock.class, new GsonLayoutBlockFactory())
                .registerTypeAdapter(TextObject.class, new GsonTextObjectFactory())
                .registerTypeAdapter(ContextBlockElement.class, new GsonContextBlockElementFactory())
                .registerTypeAdapter(BlockElement.class, new GsonBlockElementFactory())
                .registerTypeAdapterFactory(new UnknownPropertyDetectionAdapterFactory())
                .create();
    }
}
