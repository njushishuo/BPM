package com.example.lpp.examinationsystem.model;

import com.example.lpp.examinationsystem.rest.DAOFactory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateItem extends BaseMBO {

    @JsonProperty("count")
    private int count;

    @JsonProperty("item_label")
    private String label;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Label getLabelInfo() {
        return DAOFactory.getLabelDAO().getObject(label);
    }
}
