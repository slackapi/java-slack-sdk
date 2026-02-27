package com.slack.api.model.work_objects;

import com.slack.api.model.block.ImageBlock;
import com.slack.api.util.annotation.Required;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@SuperBuilder
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

    private static final class ExternalUserBuilderImpl
            extends ExternalUserBuilder<ExternalUser, ExternalUserBuilderImpl> {
        @Override
        public ExternalUser build() {
            this.userType(USER_TYPE);
            return new ExternalUser(this);
        }
    }
}
