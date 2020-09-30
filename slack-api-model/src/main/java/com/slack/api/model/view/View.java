package com.slack.api.model.view;

import com.slack.api.model.block.LayoutBlock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a surface in Slack (Modals, Home tabs)
 *
 * @see <a href="https://api.slack.com/surfaces">Slack Surfaces</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View {

    private String id;
    private String teamId;
    private String type; // modal, home, workflow_step
    private ViewTitle title;
    private ViewSubmit submit;
    private ViewClose close;
    private List<LayoutBlock> blocks;
    private String privateMetadata;
    private String callbackId;
    private String externalId;
    private ViewState state;
    private String hash;
    private Boolean clearOnClose; // must be nullable for App Home
    private Boolean notifyOnClose;  // must be nullable for App Home
    private Boolean submitDisabled; // workflow_step
    private String rootViewId;
    private String previousViewId; // views.update
    private String appId;
    private String appInstalledTeamId; // workflow_step
    private String botId;
}
