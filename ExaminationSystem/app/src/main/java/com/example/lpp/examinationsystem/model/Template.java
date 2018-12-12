package com.example.lpp.examinationsystem.model;

import com.example.lpp.examinationsystem.rest.DAOFactory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Template extends BaseMBO {

    @JsonProperty("template_name")
    private String name;

    @JsonProperty("items")
    private String items;

    @JsonProperty("recruit_id")
    private String recruitId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getRecruitId() {
        return recruitId;
    }

    public void setRecruitId(String recruitId) {
        this.recruitId = recruitId;
    }

}
