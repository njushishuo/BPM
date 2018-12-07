package com.example.lpp.examinationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelList extends BaseMBOList<Label> {

    @JsonProperty(value = "Label")
    private List<Label> list;

    @Override
    public List<Label> getList() {
        return this.list;
    }

    public void setList(List<Label> list) {
        this.list = list;
    }
}
