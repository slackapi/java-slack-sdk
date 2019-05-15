package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TeamIcon {

    private String imageOriginal;

    @SerializedName("image_34")
    private String image34;
    @SerializedName("image_44")
    private String image44;
    @SerializedName("image_68")
    private String image68;
    @SerializedName("image_88")
    private String image88;
    @SerializedName("image_102")
    private String image102;
    @SerializedName("image_132")
    private String image132;
    @SerializedName("image_230")
    private String image230;

    private boolean imageDefault;
}
