package com.example.lpp.examinationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecruitList extends BaseMBOList<Recruit> {

    @JsonProperty(value = "Recruit")
    private List<Recruit> list;

    @Override
    public List<Recruit> getList() {
        return this.list;
    }

    public void setList(List<Recruit> list) {
        this.list = list;
    }
}