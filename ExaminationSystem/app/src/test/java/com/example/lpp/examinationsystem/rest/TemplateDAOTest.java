package com.example.lpp.examinationsystem.rest;

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
        System.out.println(dao.getObject("1544099276523"));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }
}