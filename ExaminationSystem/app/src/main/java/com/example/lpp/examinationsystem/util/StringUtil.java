package com.example.lpp.examinationsystem.util;

public class StringUtil {

    private static final String SPLIT_CHAR = ";";

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
}
