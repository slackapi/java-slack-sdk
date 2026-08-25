package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.list.ListView.Grouping;
import com.slack.api.model.list.ListView.GroupingOrder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonListViewGroupingFactory implements JsonDeserializer<Grouping>, JsonSerializer<Grouping> {

    private final boolean failOnUnknownProperties;

    public GsonListViewGroupingFactory() {
        this(false);
    }

    public GsonListViewGroupingFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public Grouping deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (!json.isJsonObject()) {
            return null;
        }
        JsonObject obj = json.getAsJsonObject();
        Grouping grouping = new Grouping();

        if (obj.has("group_by") && !obj.get("group_by").isJsonNull()) {
            grouping.setGroupBy(obj.get("group_by").getAsString());
        }
        if (obj.has("group_by_column_id") && !obj.get("group_by_column_id").isJsonNull()) {
            grouping.setGroupByColumnId(obj.get("group_by_column_id").getAsString());
        }

        if (obj.has("order")) {
            JsonElement orderElem = obj.get("order");
            if (orderElem.isJsonArray()) {
                List<GroupingOrder> orderList = new ArrayList<>();
                for (JsonElement item : orderElem.getAsJsonArray()) {
                    if (item.isJsonObject()) {
                        GroupingOrder go = new GroupingOrder();
                        JsonObject itemObj = item.getAsJsonObject();
                        if (itemObj.has("select") && itemObj.get("select").isJsonArray()) {
                            List<String> selectList = new ArrayList<>();
                            for (JsonElement s : itemObj.get("select").getAsJsonArray()) {
                                selectList.add(s.getAsString());
                            }
                            go.setSelect(selectList);
                        }
                        orderList.add(go);
                    }
                }
                grouping.setOrder(orderList);
            }
            // When order is a string (e.g. ""), leave it as null
        }

        return grouping;
    }

    @Override
    public JsonElement serialize(Grouping src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("group_by", src.getGroupBy());
        obj.addProperty("group_by_column_id", src.getGroupByColumnId());
        if (src.getOrder() != null) {
            JsonArray orderArray = new JsonArray();
            for (GroupingOrder go : src.getOrder()) {
                JsonObject goObj = new JsonObject();
                if (go.getSelect() != null) {
                    JsonArray selectArray = new JsonArray();
                    for (String s : go.getSelect()) {
                        selectArray.add(s);
                    }
                    goObj.add("select", selectArray);
                }
                orderArray.add(goObj);
            }
            obj.add("order", orderArray);
        }
        return obj;
    }
}
