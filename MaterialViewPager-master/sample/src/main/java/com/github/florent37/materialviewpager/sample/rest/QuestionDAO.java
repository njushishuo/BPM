package com.github.florent37.materialviewpager.sample.rest;

import com.github.florent37.materialviewpager.sample.model.Question;
import com.github.florent37.materialviewpager.sample.model.QuestionList;
import com.github.florent37.materialviewpager.sample.util.RestInfo;

@RestInfo(path = "Question", object = Question.class, array = QuestionList.class)
public class QuestionDAO extends BaseDAO<Question, QuestionList> {
}
