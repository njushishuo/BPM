package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.Question;
import com.example.lpp.examinationsystem.model.QuestionList;
import com.example.lpp.examinationsystem.util.RestInfo;

@RestInfo(path = "Question", object = Question.class, array = QuestionList.class)
public class QuestionDAO extends BaseDAO<Question, QuestionList> {
}
