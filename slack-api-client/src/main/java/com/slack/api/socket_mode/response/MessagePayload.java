package com.slack.api.socket_mode.response;

import com.slack.api.model.Attachment;
import com.slack.api.model.block.LayoutBlock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagePayload {
    private String text;
    private List<Attachment> attachments;
    private List<LayoutBlock> blocks;
}
