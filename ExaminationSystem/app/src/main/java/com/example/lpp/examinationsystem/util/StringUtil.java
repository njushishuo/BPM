package com.example.lpp.examinationsystem.util;

import com.example.lpp.examinationsystem.model.Label;
import com.example.lpp.examinationsystem.rest.DAOFactory;
import com.example.lpp.examinationsystem.rest.LabelDAO;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    private static final String SPLIT_CHAR = ";";
    private static final String SPLIT_TEMPLATE = ";";
    private static final String SPLIT_TEMPLATE_ITEM = ",";

    public static String[] getSplits(String Ids) {
        return Ids.split(SPLIT_CHAR);
    }

    public static String buildIds(String[] splits) {
        StringBuilder builder = new StringBuilder();
        for (String id : splits) {
            builder.append(id).append(SPLIT_CHAR);
        }
        builder.deleteCharAt(builder.lastIndexOf(SPLIT_CHAR));
        return builder.toString();
    }

    public static Map<Label, Integer> parseTemplateItems(String items) {
        Map<Label, Integer> map = new HashMap<>();
        LabelDAO labelDAO = DAOFactory.getLabelDAO();

        // e.g. labelId1,labelId2,3;labelId3,5;labelId4,2
        String[] labelsPerNumber = items.split(SPLIT_TEMPLATE);
        for (String labelPerNumber : labelsPerNumber) {
            String[] labels = labelPerNumber.split(SPLIT_TEMPLATE_ITEM);
            int amount = Integer.parseInt(labels[labels.length - 1]);
            for (int i = 0; i < labels.length - 1; i++) {
                map.put(labelDAO.getObject(labels[i]), amount);
            }
        }
        return map;
    }

    public static String buildTemplateItems(Map<Label, Integer> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Label, Integer> label : map.entrySet()) {
            // TODO
        }
        return builder.toString();
    }
}
