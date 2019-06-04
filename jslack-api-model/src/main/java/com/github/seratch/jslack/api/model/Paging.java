package com.github.seratch.jslack.api.model;

import lombok.Data;

@Data
public class Paging {

    private String iid; // search
    private Integer count;
    private Integer total;
    private Integer page;
    private Integer pages;
    private Integer perPage;
    private Integer spill;
}
