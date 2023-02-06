package com.slack.api.model.admin;

import lombok.Data;

@Data
public class RoleAssignment {
    private String roleId;
    private String entityId;
    private String userId;
    private Integer dateCreate;
}
