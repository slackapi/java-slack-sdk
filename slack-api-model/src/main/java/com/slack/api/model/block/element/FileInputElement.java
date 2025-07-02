package com.slack.api.model.block.element;

import lombok.*;

import java.util.List;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/file-input-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FileInputElement extends BlockElement {
    public static final String TYPE = "file_input";
    private final String type = TYPE;

    /**
     * An identifier for the input value when the parent modal is submitted.
     * You can use this when you receive a view_submission payload to identify the value of the input element.
     * Should be unique among all other action_ids in the containing block. Maximum length is 255 characters.
     */
    private String actionId;

    /**
     * An array of valid file extensions that will be accepted for this element.
     * All file extensions will be accepted if filetypes is not specified.
     * This validation is provided for convenience only,
     * and you should perform your own file type validation based on what you expect to receive.
     */
    private List<String> filetypes;

    /**
     * Maximum number of files that can be uploaded for this file_input element.
     * Minimum of 1, maximum of 10. Defaults to 10 if not specified.
     */
    private Integer maxFiles;
}
