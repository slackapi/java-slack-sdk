package com.slack.api.model.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListLimits {
    private Boolean overRowMaximum;
    private Integer rowCountLimit;
    private Integer rowCount;
    private Boolean overColumnMaximum;
    private Integer columnCount;
    private Integer columnCountLimit;
    private Boolean overViewMaximum;
    private Integer viewCount;
    private Integer viewCountLimit;
}
