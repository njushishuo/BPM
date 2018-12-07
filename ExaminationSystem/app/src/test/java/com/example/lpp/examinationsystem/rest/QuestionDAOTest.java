package com.example.lpp.examinationsystem.rest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }
}