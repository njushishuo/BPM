package com.github.florent37.materialviewpager.sample.rest;

import com.github.florent37.materialviewpager.sample.model.Label;
import com.github.florent37.materialviewpager.sample.model.LabelList;
import com.github.florent37.materialviewpager.sample.util.RestInfo;

@RestInfo(path = "Label", object = Label.class, array = LabelList.class)
public class LabelDAO extends BaseDAO<Label, LabelList> {

}
