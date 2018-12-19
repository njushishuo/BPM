package com.github.florent37.materialviewpager.sample.rest;

import com.github.florent37.materialviewpager.sample.model.Recruit;
import com.github.florent37.materialviewpager.sample.model.RecruitList;
import com.github.florent37.materialviewpager.sample.util.RestInfo;

@RestInfo(path = "Recruit", object = Recruit.class, array = RecruitList.class)
public class RecruitDAO extends BaseDAO<Recruit, RecruitList> {
}
