package com.example.lpp.examinationsystem.model;

import com.example.lpp.examinationsystem.rest.DAOFactory;
import com.example.lpp.examinationsystem.rest.QuestionDAO;
import com.example.lpp.examinationsystem.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Paper extends BaseMBO {

    @JsonProperty("paper_name")
    private String name;

    @JsonProperty("questions")
    private String questions;

    @JsonProperty("recruit_id")
    private String recruitId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
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

    public List<Question> getQuestionsInfo() {
        QuestionDAO dao = DAOFactory.getQuestionDAO();
        List<Question> questions = new ArrayList<>();
        for (String questionId : StringUtil.getSplits(this.questions)) {
            questions.add(dao.getObject(questionId));
        }
        return questions;
    }
}
