package com.github.seratch.jslack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/composition-objects#option-group
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionGroupObject {
	private PlainTextObject label;
	private OptionObject[] options;
}
