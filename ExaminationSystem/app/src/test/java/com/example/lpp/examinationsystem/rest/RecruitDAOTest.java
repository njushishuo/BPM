package com.example.lpp.examinationsystem.rest;

import org.junit.Before;
import org.junit.Test;

public class RecruitDAOTest {

    public static RecruitDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new RecruitDAO();
    }

    @Test
    public void getObject() {
        System.out.println("Request single object");
        System.out.println(dao.getObject("1544103103620"));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }
}