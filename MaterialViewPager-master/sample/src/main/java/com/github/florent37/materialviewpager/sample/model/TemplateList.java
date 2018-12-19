package com.github.florent37.materialviewpager.sample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateList extends BaseMBOList<Template> {

    @JsonProperty(value = "Template")
    private List<Template> list;

    @Override
    public List<Template> getList() {
        return this.list;
    }

    public void setList(List<Template> list) {
        this.list = list;
    }
}