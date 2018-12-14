package com.example.lpp.examinationsystem.util;

import com.example.lpp.examinationsystem.model.Label;
import com.example.lpp.examinationsystem.model.Paper;
import com.example.lpp.examinationsystem.model.Question;
import com.example.lpp.examinationsystem.model.Recruit;
import com.example.lpp.examinationsystem.model.Template;
import com.example.lpp.examinationsystem.model.User;
import com.example.lpp.examinationsystem.rest.DAOFactory;
import com.example.lpp.examinationsystem.rest.LabelDAO;
import com.example.lpp.examinationsystem.rest.QuestionDAO;

import java.util.ArrayList;
import java.util.List;

public class RestUtil {

    public static List<Label> getLabelsInfo(Question question) {
        LabelDAO dao = DAOFactory.getLabelDAO();
        List<Label> labels = new ArrayList<>();
        for (String labelId : StringUtil.getSplits(question.getLabels())) {
            labels.add(dao.getObject(labelId));
        }
        return labels;
    }

    public static List<Question> getQuestionsInfo(Paper paper) {
        QuestionDAO dao = DAOFactory.getQuestionDAO();
        List<Question> questions = new ArrayList<>();
        for (String questionId : StringUtil.getSplits(paper.getQuestions())) {
            questions.add(dao.getObject(questionId));
        }
        return questions;
    }

    public static Recruit getRecruitInfo(Paper paper) {
        return DAOFactory.getRecruitDAO().getObject(paper.getRecruitId());
    }

    public static Recruit getRecruitInfo(Template template) {
        return DAOFactory.getRecruitDAO().getObject(template.getRecruitId());
    }

    public static User getOwnerInfo(Recruit recruit) {
        return DAOFactory.getUserDAO().getObject(recruit.getOwnerId());
    }

    public static List<Paper> getPapersByRecruit(Recruit recruit) {
        return DAOFactory.getPaperDAO().getPapersByRecruit(String.valueOf(recruit.getId()));
    }

    public static List<Template> getTemplatesByRecruit(Recruit recruit) {
        return DAOFactory.getTemplateDAO().getTemplatesByRecruit(String.valueOf(recruit.getId()));
    }
}
