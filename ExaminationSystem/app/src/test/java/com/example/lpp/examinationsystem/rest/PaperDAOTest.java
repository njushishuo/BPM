package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.Paper;

import org.junit.Before;
import org.junit.Test;

public class PaperDAOTest {

    public static PaperDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new PaperDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        Paper paper = dao.getObject("1544617612330");
        System.out.println(paper);
        System.out.println(paper.getQuestionsInfo());
        System.out.println(paper.getRecruitInfo());
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }

    @Test
    public void postObject() {
        System.out.println("Post Object");
        Paper paper = new Paper();
        paper.setName("Java Paper v1");
        paper.setQuestions("1544615560340");
        paper.setRecruitId("1544616954639");
        System.out.println(dao.postObject(paper));
    }
}