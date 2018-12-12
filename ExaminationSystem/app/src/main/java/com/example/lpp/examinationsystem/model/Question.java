package com.example.lpp.examinationsystem.model;

import com.example.lpp.examinationsystem.rest.DAOFactory;
import com.example.lpp.examinationsystem.rest.LabelDAO;
import com.example.lpp.examinationsystem.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Question extends BaseMBO {

    @JsonProperty("question_desc")
    private String description;

    @JsonProperty("answer")
    private String answer;

    @JsonProperty("question_type")
    private String questionType;

    @JsonProperty("labels")
    private String labels;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public List<Label> getLabelsInfo() {
        LabelDAO dao = DAOFactory.getLabelDAO();
        List<Label> labels = new ArrayList<>();
        for (String labelId : StringUtil.getSplits(this.labels)) {
            labels.add(dao.getObject(labelId));
        }
        return labels;
    }
}
