package com.example.lpp.examinationsystem.model;

import java.util.List;

public abstract class BaseMBOList<T extends BaseMBO> {

    public abstract List<T> getList();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (T object : getList()) {
            builder.append(object.toString()).append("\n");
        }
        return builder.toString();
    }
}
