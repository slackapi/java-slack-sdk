package com.slack.api.model;

import lombok.Data;

import java.util.List;

@Data
public class WarningResponseMetadata {

    private List<String> warnings;
}
