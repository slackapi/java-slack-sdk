package com.github.seratch.jslack.api.model.block.element;

import com.github.seratch.jslack.api.model.block.composition.ConfirmationDialogObject;
import com.github.seratch.jslack.api.model.block.composition.OptionGroupObject;
import com.github.seratch.jslack.api.model.block.composition.OptionObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * https://api.slack.com/reference/messaging/block-elements#static-select
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaticSelectElement extends BlockElement {
    private final String type = "static_select";
    private PlainTextObject placeholder;
    private String actionId;
    @Builder.Default
    private List<OptionObject> options = new ArrayList<>();
    @Builder.Default
    private List<OptionGroupObject> optionGroups = new ArrayList<>();
    private OptionObject initialOption;
    private ConfirmationDialogObject confirm;
}
