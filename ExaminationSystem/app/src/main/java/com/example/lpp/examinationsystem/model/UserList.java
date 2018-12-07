package com.example.lpp.examinationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserList extends BaseMBOList<User> {

    @JsonProperty(value = "User")
    private List<User> list;

    @Override
    public List<User> getList() {
        return this.list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}