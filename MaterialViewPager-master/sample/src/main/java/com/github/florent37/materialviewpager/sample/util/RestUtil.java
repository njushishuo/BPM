package com.github.florent37.materialviewpager.sample.util;

import com.github.florent37.materialviewpager.sample.model.Label;
import com.github.florent37.materialviewpager.sample.model.Paper;
import com.github.florent37.materialviewpager.sample.model.Question;
import com.github.florent37.materialviewpager.sample.model.Recruit;
import com.github.florent37.materialviewpager.sample.model.Template;
import com.github.florent37.materialviewpager.sample.model.User;
import com.github.florent37.materialviewpager.sample.rest.DAOFactory;
import com.github.florent37.materialviewpager.sample.rest.LabelDAO;
import com.github.florent37.materialviewpager.sample.rest.QuestionDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RestUtil {

    public static List<Label> getLabelsInfo(Question question) {
        LabelDAO dao = DAOFactory.getLabelDAO();
        List<Label> labels = new ArrayList<>();
        for (String labelId : StringUtil.getSplits(question.getLabels())) {
            labels.add(dao.getObject(labelId));
        }
        return labels;
    }

    public static List<Question> getQuestionsInfo(Paper paper) {
        QuestionDAO dao = DAOFactory.getQuestionDAO();
        List<Question> questions = new ArrayList<>();
        for (String questionId : StringUtil.getSplits(paper.getQuestions())) {
            questions.add(dao.getObject(questionId));
        }
        return questions;
    }

    public static Recruit getRecruitInfo(Paper paper) {
        return DAOFactory.getRecruitDAO().getObject(paper.getRecruitId());
    }

    public static Recruit getRecruitInfo(Template template) {
        return DAOFactory.getRecruitDAO().getObject(template.getRecruitId());
    }

    public static User getOwnerInfo(Recruit recruit) {
        return DAOFactory.getUserDAO().getObject(recruit.getOwnerId());
    }

    public static List<Paper> getPapersByRecruit(Recruit recruit) {
        return DAOFactory.getPaperDAO().getPapersByRecruit(String.valueOf(recruit.getId()));
    }

    public static List<Template> getTemplatesByRecruit(Recruit recruit) {
        return DAOFactory.getTemplateDAO().getTemplatesByRecruit(String.valueOf(recruit.getId()));
    }

    public static String getQuestionsByTemplate(Template template) {
        Map<Label, Integer> map = StringUtil.parseTemplateItems(template.getItems());
        int amount = 0;
        for (Map.Entry<Label, Integer> label : map.entrySet()) {
            amount += label.getValue();
        }
        List<Question> all = DAOFactory.getQuestionDAO().getList();
        List<Question> res = new ArrayList<>();
        if (all.size() == 0)
            return "";
        if (amount >= all.size()) {
            res = all;
        } else {
            int[] positions = randomArray(0, all.size() - 1, amount);
            for (int pos : positions) {
                res.add(all.get(pos));
            }
        }
        String[] splits = new String[res.size()];
        for (int i = 0; i < splits.length; i++) {
            splits[i] = String.valueOf(res.get(i).getId());
        }
        return StringUtil.buildIds(splits);
    }

    private static int[] randomArray(int min, int max, int n) {
        int len = max - min + 1;

        if (max < min || n > len) {
            return null;
        }

        //初始化给定范围的待选数组
        int[] source = new int[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }
}
