package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.block.DataTableCell;
import com.slack.api.model.block.RawNumberDataTableCell;
import com.slack.api.model.block.RawTextDataTableCell;
import com.slack.api.model.block.RichTextBlock;

import java.lang.reflect.Type;

/**
 * Factory for deserializing the cells of a
 * {@link com.slack.api.model.block.DataTableBlock data table block}.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-table-block">Data table block</a>
 */
public class GsonDataTableCellFactory implements JsonDeserializer<DataTableCell>, JsonSerializer<DataTableCell> {

    private final boolean failOnUnknownProperties;

    public GsonDataTableCellFactory() {
        this(false);
    }

    public GsonDataTableCellFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public DataTableCell deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get("type");
        final String typeName = prim.getAsString();
        final Class<? extends DataTableCell> clazz = getDataTableCellClassInstance(typeName);
        return context.deserialize(jsonObject, clazz);
    }

    @Override
    public JsonElement serialize(DataTableCell src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends DataTableCell> getDataTableCellClassInstance(String typeName) {
        switch (typeName) {
            case RawTextDataTableCell.TYPE:
                return RawTextDataTableCell.class;
            case RawNumberDataTableCell.TYPE:
                return RawNumberDataTableCell.class;
            case RichTextBlock.TYPE:
                return RichTextBlock.class;
            default:
                throw new JsonParseException("Unknown data table cell type: " + typeName);
        }
    }
}
