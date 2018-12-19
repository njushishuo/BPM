package com.github.florent37.materialviewpager.sample.util;

import com.github.florent37.materialviewpager.sample.model.Label;
import com.github.florent37.materialviewpager.sample.model.Paper;
import com.github.florent37.materialviewpager.sample.model.Question;
import com.github.florent37.materialviewpager.sample.model.Recruit;
import com.github.florent37.materialviewpager.sample.model.Template;
import com.github.florent37.materialviewpager.sample.model.User;
import com.github.florent37.materialviewpager.sample.rest.DAOFactory;
import com.github.florent37.materialviewpager.sample.rest.LabelDAO;
import com.github.florent37.materialviewpager.sample.rest.QuestionDAO;

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
