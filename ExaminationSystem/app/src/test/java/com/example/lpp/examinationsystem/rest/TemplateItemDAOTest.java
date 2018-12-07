package com.example.lpp.examinationsystem.rest;

import org.junit.Before;
import org.junit.Test;

public class TemplateItemDAOTest {

    public static TemplateItemDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new TemplateItemDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        System.out.println(dao.getObject("1544090381718"));
        System.out.println(dao.getObject("1544090429720"));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }
}