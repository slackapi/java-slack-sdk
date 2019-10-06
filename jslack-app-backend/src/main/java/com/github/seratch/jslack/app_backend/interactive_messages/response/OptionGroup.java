package com.github.seratch.jslack.app_backend.interactive_messages.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionGroup {
    private String label;
    private List<Option> options;
}
