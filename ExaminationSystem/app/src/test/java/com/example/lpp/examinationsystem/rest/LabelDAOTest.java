package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.Label;

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
        System.out.println(dao.getObject("1544614143626"));
    }

    @Test
    public void putObject() {
        System.out.println("Put single object");
        Label label = dao.getObject("1544614143626");
        String name = "Data Structure";
        label.setName("Test_Labels");
        System.out.println(dao.putObject(label));
        label.setName(name);
        System.out.println(dao.putObject(label));
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }

    @Test
    public void postObject() {
        System.out.println("Post Object");
        Label label = new Label();
        label.setName("JAVA");
        System.out.println(dao.postObject(label));
    }
}