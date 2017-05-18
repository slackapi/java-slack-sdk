package com.github.seratch.jslack.api.methods.request.users;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

import java.io.File;

@Data
@Builder
public class UsersSetPhotoRequest implements SlackApiRequest {

    private String token;
    private File image;
    private Integer cropX;
    private Integer cropY;
    private Integer cropW;
}
