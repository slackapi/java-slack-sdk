package com.github.seratch.jslack.app_backend.slash_commands.response;

import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://api.slack.com/docs/interactive-message-field-guide
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlashCommandResponse {
    private String responseType; // ephemeral, in_channel
    private String text;
    private List<Attachment> attachments;
    private List<LayoutBlock> blocks;
}
