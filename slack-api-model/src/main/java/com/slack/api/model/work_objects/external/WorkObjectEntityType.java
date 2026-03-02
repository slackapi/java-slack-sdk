package com.slack.api.model.work_objects.external;

import com.google.gson.annotations.SerializedName;
import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public enum WorkObjectEntityType {
    @SerializedName("slack#/entities/task") TASK("slack#/entities/task"),
    @SerializedName("slack#/entities/file") FILE("slack#/entities/file"),
    @SerializedName("slack#/entities/item") ITEM("slack#/entities/item"),
    @SerializedName("slack#/entities/incident") INCIDENT("slack#/entities/incident"),
    @SerializedName("slack#/entities/content_item") CONTENT_ITEM("slack#/entities/content_item"),
    @SerializedName("slack#/entities/tableau_analytics") TABLEAU_ANALYTICS("slack#/entities/tableau_analytics"),
    @SerializedName("slack#/entities/calendar_event") CALENDAR_EVENT("slack#/entities/calendar_event");

    private final String type;
}
