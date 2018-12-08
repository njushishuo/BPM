package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.Label;
import com.example.lpp.examinationsystem.model.Question;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class QuestionDAOTest {

    public static QuestionDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new QuestionDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        System.out.println(dao.getObject("1544080236674"));
        System.out.println(dao.getObject("1544080345103"));
    }

    @Test
    public void putObject() {
        System.out.println("Put single object");
        Question question = dao.getObject("1544080236674");
        String answer = "/* correct answer */";
//        List<Label> labels = question.getLabels();
        question.setAnswer("Test_Answer");
//        labels.remove(0);
//        question.setLabels(labels);
        System.out.println(dao.putObject("1544080236674", question));
        question.setAnswer(answer);
        System.out.println(dao.putObject("1544080236674", question));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }
}