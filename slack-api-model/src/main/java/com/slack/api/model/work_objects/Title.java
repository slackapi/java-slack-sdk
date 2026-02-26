package com.slack.api.model.work_objects;


import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Title {
    @Required String text;
}
