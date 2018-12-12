package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.Template;

import org.junit.Before;
import org.junit.Test;

public class TemplateDAOTest {

    public static TemplateDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new TemplateDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        Template template = dao.getObject("1544618242313");
        System.out.println(template);
        System.out.println(template.getItemsInfo());
        System.out.println(template.getRecruitInfo());
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }

    @Test
    public void postObject() {
        System.out.println("Post Object");
        Template template = new Template();
        template.setName("Java Template v1");
        template.setItems("1544618086251");
        template.setRecruitId("1544616954639");
        System.out.println(dao.postObject(template));
    }
}