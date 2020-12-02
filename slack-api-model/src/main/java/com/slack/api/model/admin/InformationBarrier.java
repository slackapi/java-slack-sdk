package com.slack.api.model.admin;

import lombok.Data;

import java.util.List;

@Data
public class InformationBarrier {
    private String id;
    private String enterpriseId;
    private IdpUserGroup primaryUsergroup;
    private List<IdpUserGroup> barrieredFromUsergroups;
    private List<String> restrictedSubjects;
    private Integer dateUpdate;
}
