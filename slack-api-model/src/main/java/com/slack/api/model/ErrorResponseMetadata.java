package com.slack.api.model;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponseMetadata {

    private List<String> messages;
    private List<String> warnings;
}
