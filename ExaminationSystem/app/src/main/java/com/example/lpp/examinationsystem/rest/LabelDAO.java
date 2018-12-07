package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.Label;
import com.example.lpp.examinationsystem.model.LabelList;
import com.example.lpp.examinationsystem.util.RestInfo;

@RestInfo(path = "Label", object = Label.class, array = LabelList.class)
public class LabelDAO extends BaseDAO<Label, LabelList> {

}
