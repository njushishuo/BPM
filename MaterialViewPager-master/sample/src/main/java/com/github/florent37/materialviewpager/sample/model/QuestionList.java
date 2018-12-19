package com.github.florent37.materialviewpager.sample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionList extends BaseMBOList<Question> {

    @JsonProperty(value = "Question")
    private List<Question> list;

    @Override
    public List<Question> getList() {
        return this.list;
    }

    public void setList(List<Question> list) {
        this.list = list;
    }
}
