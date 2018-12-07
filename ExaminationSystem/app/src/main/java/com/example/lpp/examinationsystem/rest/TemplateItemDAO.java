package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.TemplateItem;
import com.example.lpp.examinationsystem.model.TemplateItemList;
import com.example.lpp.examinationsystem.util.RestInfo;

@RestInfo(path = "Template_item", object = TemplateItem.class, array = TemplateItemList.class)
public class TemplateItemDAO extends BaseDAO<TemplateItem, TemplateItemList> {
}
