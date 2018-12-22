package com.github.florent37.materialviewpager.sample.model;

import com.github.florent37.materialviewpager.sample.util.RestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaperVO {

    public PaperVO(Paper paper) {
        this.name = paper.getName();
        this.recruit = RestUtil.getRecruitInfo(paper);
        this.questions = RestUtil.getQuestionsInfo(paper);
        this.labelsMap = new HashMap<>();
        for (Question question : questions) {
            List<Label> labels = RestUtil.getLabelsInfo(question);
            for (Label label : labels) {
                String labelName = label.getName();
                if (labelsMap.containsKey(labelName)) {
                    labelsMap.put(labelName, labelsMap.get(labelName) + 1);
                } else {
                    labelsMap.put(labelName, 1);
                }
            }
        }
    }

    private String name;

    private Recruit recruit;

    private List<Question> questions;

    private Map<String, Integer> labelsMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Recruit getRecruit() {
        return recruit;
    }

    public void setRecruit(Recruit recruit) {
        this.recruit = recruit;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Map<String, Integer> getLabelsMap() {
        return labelsMap;
    }

    public void setLabelsMap(Map<String, Integer> labelsMap) {
        this.labelsMap = labelsMap;
    }

    public String analysisInfo() {
        StringBuilder builder = new StringBuilder();
        List<Question> questions = this.getQuestions();
        builder.append("本试卷属于");
        switch (recruit.getType()) {
            case "WEB":
                builder.append("网页开发");
                break;
            case "APP":
                builder.append("移动开发");
                break;
            default:
                builder.append("小程序开发");
                break;
        }
        builder.append("方向，共有").append(questions.size()).append("道试题，包括");
        int[] amouts = new int[2];
        for (Question question : questions) {
            if (question.getQuestionType().equals("ESSAY")) amouts[0]++;
            else amouts[1]++;
        }
        builder.append(amouts[1]).append("道选择题和").append(amouts[0]).append("道简答题。\n");
        builder.append("试题的考察内容主要涉及");
        int max = 0;
        List<String> strings = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : labelsMap.entrySet()) {
            if (entry.getValue() > max) {
                strings = new ArrayList<>();
                strings.add(entry.getKey());
            } else if (entry.getValue() == max) {
                strings.add(entry.getKey());
            }
        }
        for (String label : strings) {
            builder.append(label).append("、");
        }
        builder.deleteCharAt(builder.lastIndexOf("、"));
        builder.append("等。");
        return builder.toString();
    }
}
