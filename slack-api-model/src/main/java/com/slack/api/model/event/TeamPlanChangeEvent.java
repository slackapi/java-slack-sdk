package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * The team_plan_change event is sent to all connections for a workspace when the current billing plan is changed.
 * Currently possible values are: empty string, comp, std, plus.
 * <p>
 * https://docs.slack.dev/reference/events/team_plan_change
 */
@Data
public class TeamPlanChangeEvent implements Event {

    public static final String TYPE_NAME = "team_plan_change";

    private final String type = TYPE_NAME;
    private String plan; // possible values are: empty string, comp, std, plus
    private boolean canAddUra;
    private List<String> paidFeatures;

}
