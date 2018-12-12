package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.Question;
import com.example.lpp.examinationsystem.util.StringUtil;

import org.junit.Before;
import org.junit.Test;

public class QuestionDAOTest {

    public static QuestionDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new QuestionDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        Question question = dao.getObject("1544615560340");
        System.out.println(question);
        System.out.println(question.getLabelsInfo());
    }

    @Test
    public void putObject() {
        System.out.println("Put single object");
        Question question = dao.getObject("1544615560340");
        question.setQuestionType("ESSAY");
        System.out.println(dao.putObject(question));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }

    @Test
    public void postObject() {
        System.out.println("Post Object");
        Question question = new Question();
        question.setDescription("Describe TCP?");
        question.setAnswer("TCP is just TCP!");
        question.setQuestionType("essay");
        question.setLabels("");
        System.out.println(dao.postObject(question));
    }
}