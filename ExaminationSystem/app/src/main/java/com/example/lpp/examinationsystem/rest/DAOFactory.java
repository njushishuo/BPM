package com.example.lpp.examinationsystem.rest;

public class DAOFactory {

    private static LabelDAO labelDAO = new LabelDAO();
    private static PaperDAO paperDAO = new PaperDAO();
    private static QuestionDAO questionDAO = new QuestionDAO();
    private static RecruitDAO recruitDAO = new RecruitDAO();
    private static TemplateDAO templateDAO = new TemplateDAO();
    private static UserDAO userDAO = new UserDAO();

    public static LabelDAO getLabelDAO() {
        return labelDAO;
    }

    public static PaperDAO getPaperDAO() {
        return paperDAO;
    }

    public static QuestionDAO getQuestionDAO() {
        return questionDAO;
    }

    public static RecruitDAO getRecruitDAO() {
        return recruitDAO;
    }

    public static TemplateDAO getTemplateDAO() {
        return templateDAO;
    }

    public static UserDAO getUserDAO() {
        return userDAO;
    }
}

