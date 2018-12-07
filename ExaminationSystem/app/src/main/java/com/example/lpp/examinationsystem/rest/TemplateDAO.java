package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.Template;
import com.example.lpp.examinationsystem.model.TemplateList;
import com.example.lpp.examinationsystem.util.RestInfo;

@RestInfo(path = "Template", object = Template.class, array = TemplateList.class)
public class TemplateDAO extends BaseDAO<Template, TemplateList> {
}
