package com.slack.api.model.work_objects;

import com.slack.api.model.block.composition.SlackFileObject;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Image {
    @Required private String type;
    @Required private String altText;
    private String imageUrl;
    private String title;
    private SlackFileObject slackFile;
}
