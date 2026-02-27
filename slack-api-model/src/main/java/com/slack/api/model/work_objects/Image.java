package com.slack.api.model.work_objects;

import com.slack.api.model.block.composition.SlackFileObject;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Image {
    @Required String type;
    @Required String altText;
    String imageUrl;
    String title;
    SlackFileObject slackFile;
}
