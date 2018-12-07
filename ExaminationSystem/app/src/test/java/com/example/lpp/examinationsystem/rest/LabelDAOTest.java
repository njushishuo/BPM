package com.example.lpp.examinationsystem.rest;

import org.junit.Before;
import org.junit.Test;

public class LabelDAOTest {

    public static LabelDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new LabelDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        System.out.println(dao.getObject("1544078682813"));
        System.out.println(dao.getObject("1544078713499"));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }
}