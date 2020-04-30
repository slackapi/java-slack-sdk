package com.slack.api.model.dialog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A dialog element option group used with {@link DialogSelectElement}s
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogOptionGroup {

    /**
     * Label displayed to user. Required. 75 characters maximum.
     */
    private String label;

    /**
     * Provide up to 100 option element attributes.
     */
    private List<DialogOption> options;


}
