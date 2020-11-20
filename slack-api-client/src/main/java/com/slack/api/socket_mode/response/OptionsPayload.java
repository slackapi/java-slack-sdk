package com.slack.api.socket_mode.response;

import com.slack.api.model.block.composition.OptionGroupObject;
import com.slack.api.model.block.composition.OptionObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionsPayload {
    private List<OptionObject> options;
    private List<OptionGroupObject> optionGroups;
}
