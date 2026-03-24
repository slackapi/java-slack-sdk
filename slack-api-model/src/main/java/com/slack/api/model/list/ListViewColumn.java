package com.slack.api.model.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListViewColumn {
    private boolean visible;
    private String key;
    private String id;
    private String position;
    private Integer width;
    private boolean shouldWrapText;
}
