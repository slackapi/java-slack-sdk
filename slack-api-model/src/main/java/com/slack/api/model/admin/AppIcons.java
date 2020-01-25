package com.slack.api.model.admin;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AppIcons {
    @SerializedName("image_32")
    private String image32;
    @SerializedName("image_36")
    private String image36;
    @SerializedName("image_48")
    private String image48;
    @SerializedName("image_64")
    private String image64;
    @SerializedName("image_72")
    private String image72;
    @SerializedName("image_96")
    private String image96;
    @SerializedName("image_128")
    private String image128;
    @SerializedName("image_192")
    private String image192;
    @SerializedName("image_512")
    private String image512;
    @SerializedName("image_1024")
    private String image1024;

    private String imageOriginal;
}
