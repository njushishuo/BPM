package com.github.florent37.materialviewpager.sample.model;

import java.io.Serializable;
import java.util.List;

public abstract class BaseMBOList<T extends BaseMBO> implements Serializable {

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
