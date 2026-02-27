package com.slack.api.model.work_objects;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Layouts {
    CompactLayout compact;
    ExpandedLayout expanded;
    FullLayout full;
    MinimalLayout minimal;
}
