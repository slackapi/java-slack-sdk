package com.slack.api.model.work_objects.external;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TagColor {
    @SerializedName("flamingo") FLAMINGO("flamingo"),
    @SerializedName("honeycomb") HONEYCOMB("honeycomb"),
    @SerializedName("grass") GRASS("grass"),
    @SerializedName("gray") GREY("gray"),
    @SerializedName("informative") INFORMATIVE("informative"),
    @SerializedName("indigo") INDIGO("indigo"),
    @SerializedName("lagooon") LAGOON("lagoon"),
    @SerializedName("jade") JADE("jade"),
    @SerializedName("horchata") HORCHATA("horchata"),
    @SerializedName("aubergine") AUBERGINE("aubergine");

    private final String color;
}
