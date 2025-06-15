package com.slack.api.model;

import lombok.Data;

import java.util.List;

@Data
public class Paging {

    private String iid; // search
    private Integer count;
    private Integer total;
    private Integer page;
    private Integer pages;
    private Integer perPage;
    private Integer spill;
    private List<String> warnings;
}
