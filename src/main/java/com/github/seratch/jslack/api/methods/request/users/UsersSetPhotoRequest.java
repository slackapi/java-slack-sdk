package com.github.seratch.jslack.api.methods.request.users;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.io.File;

/**
 * Set the user profile photo
 */
@Data
@Builder
public class UsersSetPhotoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users.profile:write`
     */
    private String token;

    /**
     * File contents via `multipart/form-data`.
     */
    private File image;

    /**
     * X coordinate of top-left corner of crop box
     */
    private Integer cropX;

    /**
     * Y coordinate of top-left corner of crop box
     */
    private Integer cropY;

    /**
     * Width/height of crop box (always square)
     */
    private Integer cropW;

}
