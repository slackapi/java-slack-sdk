package com.slack.api.model.work_objects;

import com.slack.api.util.annotation.Required;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Fields {
    @Required String type;
    @Required List<Field> elements;
}
