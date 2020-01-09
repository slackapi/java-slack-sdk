package com.github.seratch.jslack.api.model.admin;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AppScope {
    private String name;
    private String description;
    @SerializedName("is_sensitive")
    private boolean sensitive;
    private String tokenType;
}
