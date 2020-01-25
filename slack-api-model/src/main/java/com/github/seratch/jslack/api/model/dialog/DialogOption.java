package com.github.seratch.jslack.api.model.dialog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A dialog element option used with {@link DialogSelectElement}s
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogOption {
    private String label;
    private String value;
}
