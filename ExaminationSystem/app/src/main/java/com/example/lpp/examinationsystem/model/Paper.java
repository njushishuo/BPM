package com.example.lpp.examinationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Paper extends BaseMBO {

    @JsonProperty("description")
    private String description;

    @JsonProperty("recruitid")
    private long recruitid;

    @JsonProperty("questions")
    private List<Question> questions;

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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
