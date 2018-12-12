package com.example.lpp.examinationsystem.model;

import com.example.lpp.examinationsystem.rest.DAOFactory;
import com.example.lpp.examinationsystem.rest.TemplateItemDAO;
import com.example.lpp.examinationsystem.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

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

    public Recruit getRecruitInfo() {
        return DAOFactory.getRecruitDAO().getObject(recruitId);
    }

    public List<TemplateItem> getItemsInfo() {
        TemplateItemDAO dao = DAOFactory.getTemplateItemDAO();
        List<TemplateItem> items = new ArrayList<>();
        for (String itemId : StringUtil.getSplits(this.items)) {
            items.add(dao.getObject(itemId));
        }
        return items;
    }
}
