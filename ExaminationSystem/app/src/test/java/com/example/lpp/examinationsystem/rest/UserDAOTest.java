package com.example.lpp.examinationsystem.rest;

import org.junit.Before;
import org.junit.Test;

public class UserDAOTest {

    public static UserDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new UserDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        System.out.println(dao.getObject("1544102864314"));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }
}