package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.Recruit;
import com.example.lpp.examinationsystem.model.RecruitList;
import com.example.lpp.examinationsystem.util.RestInfo;

@RestInfo(path = "Recruit", object = Recruit.class, array = RecruitList.class)
public class RecruitDAO extends BaseDAO<Recruit, RecruitList> {
}
