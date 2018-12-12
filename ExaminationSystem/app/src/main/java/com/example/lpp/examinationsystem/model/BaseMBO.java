package com.example.lpp.examinationsystem.model;

import android.util.Log;

import com.example.lpp.examinationsystem.util.ReflectionUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.lang.reflect.Field;

public class BaseMBO implements Serializable {

    @JsonProperty("id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        try {
            Field[] fields = ReflectionUtil.getFields(this.getClass());
            String name = this.getClass().getName();
            builder.append(name.substring(name.lastIndexOf(".") + 1)).append(" {");
            for (Field f : fields) {
                f.setAccessible(true);
                builder.append(f.getName()).append("=");
                builder.append(ReflectionUtil.getFieldValue(this, f)).append(",");
            }
            builder.deleteCharAt(builder.lastIndexOf(","));
            builder.append("}");
        } catch (IllegalAccessException e) {
            Log.e("BaseMBO Error", e.getMessage(), e);
        }
        return builder.toString();
    }
}
