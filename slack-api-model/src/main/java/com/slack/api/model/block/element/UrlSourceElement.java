package com.slack.api.model.block.element;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/url-source-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlSourceElement {
    public static final String TYPE = "url";
    private final String type = TYPE;

    private String url;
    private String text;
}
