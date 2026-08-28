package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.block.RawNumberTableCell;
import com.slack.api.model.block.RawTextTableCell;
import com.slack.api.model.block.RichTextBlock;
import com.slack.api.model.block.TableCell;

import java.lang.reflect.Type;

/**
 * Factory for deserializing the cells of a
 * {@link com.slack.api.model.block.TableBlock table block}.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/table-block">Table block</a>
 */
public class GsonTableCellFactory implements JsonDeserializer<TableCell>, JsonSerializer<TableCell> {

    private final boolean failOnUnknownProperties;

    public GsonTableCellFactory() {
        this(false);
    }

    public GsonTableCellFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public TableCell deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String typeName = prim.getAsString();
        final Class<? extends TableCell> clazz = getTableCellClassInstance(typeName);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(TableCell src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends TableCell> getTableCellClassInstance(String typeName) {
        switch (typeName) {
            case RawTextTableCell.TYPE:
                return RawTextTableCell.class;
            case RawNumberTableCell.TYPE:
                return RawNumberTableCell.class;
            case RichTextBlock.TYPE:
                return RichTextBlock.class;
            default:
                throw new JsonParseException("Unknown table cell type: " + typeName);
        }
    }
}
