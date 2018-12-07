package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.Paper;
import com.example.lpp.examinationsystem.model.PaperList;
import com.example.lpp.examinationsystem.util.RestInfo;

@RestInfo(path = "Paper", object = Paper.class, array = PaperList.class)
public class PaperDAO extends BaseDAO<Paper, PaperList> {
}
