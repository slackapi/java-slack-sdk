package com.github.seratch.jslack.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Option {
    private String text;
    private String value;
}
