package com.example.lpp.examinationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateItemList extends BaseMBOList<TemplateItem>  {

    @JsonProperty(value = "Template_item")
    private List<TemplateItem> list;

    @Override
    public List<TemplateItem> getList() {
        return this.list;
    }

    public void setList(List<TemplateItem> list) {
        this.list = list;
    }
}