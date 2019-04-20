package com.github.seratch.jslack.app_backend.interactive_messages.response;

import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

@Data
public class ActionResponse {
    private String responseType; // ephemeral, in_channel
    private String text;
    private boolean replaceOriginal;
    private boolean deleteOriginal;
    private List<Attachment> attachments;
    private List<LayoutBlock> blocks;
}
