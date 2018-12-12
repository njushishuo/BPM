package com.example.lpp.examinationsystem;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lpp.examinationsystem.model.Paper;
import com.example.lpp.examinationsystem.model.Recruit;
import com.example.lpp.examinationsystem.rest.RecruitDAO;
import com.example.lpp.examinationsystem.util.RestUtil;

import java.lang.reflect.Array;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class PaperAdapter extends ArrayAdapter<Paper> {
    int resourceId;
    private Paper paper;
    private TextView project_name;
    public PaperAdapter(Context context, int textViewResourceId, List<Paper> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        paper=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView paper_id=(TextView) view.findViewById(R.id.paper_id);
        project_name=(TextView) view.findViewById(R.id.project_name);
        TextView paper_description=(TextView) view.findViewById(R.id.paper_description);
        paper_id.setText(String.valueOf(paper.getId()));
        paper_description.setText(paper.getName());
        project_name.setText(String.valueOf(RestUtil.getRecruitInfo(paper).getName()));
//        RecruitDAO recruitDAO=new RecruitDAO();
//        String recruitid=String.valueOf(paper.getRecruitid());
//        Recruit recruit=recruitDAO.getObject(recruitid);
//        System.out.println(recruit);
//        project_name.setText(recruit.getName());
        return view;
    }

}
