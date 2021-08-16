package com.slack.api.model.connect;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ConnectUserProfile {
    private String realName;
    private String realNameNormalized;
    private String displayName;
    private String displayNameNormalized;
    private String team;
    private String avatarHash;
    private String email;
    @SerializedName("image_24")
    private String image24;
    @SerializedName("image_32")
    private String image32;
    @SerializedName("image_48")
    private String image48;
    @SerializedName("image_72")
    private String image72;
    @SerializedName("image_192")
    private String image192;
    @SerializedName("image_512")
    private String image512;
    @SerializedName("image_1024")
    private String image1024;
    private String imageOriginal;
    private Boolean isCustomImage;
}
