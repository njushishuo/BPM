package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.TemplateItem;

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
        TemplateItem item = dao.getObject("1544618086251");
        System.out.println(item);
        System.out.println(item.getLabelInfo());
    }

    @Test
    public void getList() {
        System.out.println("Request list");
        System.out.println(dao.getList());
    }

    @Test
    public void postObject() {
        System.out.println("Post Object");
        TemplateItem item = new TemplateItem();
        item.setCount(2);
        item.setLabel("1544614143626");
        System.out.println(dao.postObject(item));
    }
}