package com.example.lpp.examinationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaperList extends BaseMBOList<Paper> {

    @JsonProperty(value = "Paper")
    private List<Paper> list;

    @Override
    public List<Paper> getList() {
        return this.list;
    }

    public void setList(List<Paper> list) {
        this.list = list;
    }
}