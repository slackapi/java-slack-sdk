package com.github.seratch.jslack.api.model.dialog;

import lombok.Builder;
import lombok.Data;

/**
 * A dialog element option used with {@link DialogSelectElement}s
 */
@Data
@Builder
public class DialogOption {
    private String label;
    private String value;
}
