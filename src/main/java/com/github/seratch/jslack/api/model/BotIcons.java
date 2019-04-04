package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BotIcons {
    @SerializedName("image_36")
    private String image36;
    @SerializedName("image_48")
    private String image48;
    @SerializedName("image_72")
    private String image72;
}
