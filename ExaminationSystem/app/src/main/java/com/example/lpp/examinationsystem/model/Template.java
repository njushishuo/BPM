package com.example.lpp.examinationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Template extends BaseMBO {

    @JsonProperty("description")
    private String description;

    @JsonProperty("recruitid")
    private long recruitid;

    @JsonProperty("items")
    private List<TemplateItem> items;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRecruitid() {
        return recruitid;
    }

    public void setRecruitid(long recruitid) {
        this.recruitid = recruitid;
    }

    public List<TemplateItem> getItems() {
        return items;
    }

    public void setItems(List<TemplateItem> items) {
        this.items = items;
    }
}
