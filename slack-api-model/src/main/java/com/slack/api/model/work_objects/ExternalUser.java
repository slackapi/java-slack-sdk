package com.slack.api.model.work_objects;

import com.slack.api.model.block.ImageBlock;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ExternalUser extends User {
    public static final String USER_TYPE = "external";
    @Required String text;
    ImageBlock image;
    String url;
    String email;
    String caption;
    UserMetadata userMetadata;

    @Builder
    public ExternalUser(
            String text,
            ImageBlock image,
            String url,
            String email,
            String caption,
            UserMetadata userMetadata
    ) {
        super(USER_TYPE);
        this.text = text;
        this.image = image;
        this.url = url;
        this.email = email;
        this.caption = caption;
        this.userMetadata = userMetadata;
    }
}
