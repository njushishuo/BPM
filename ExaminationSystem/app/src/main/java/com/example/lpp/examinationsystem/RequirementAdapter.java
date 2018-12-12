package com.example.lpp.examinationsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lpp.examinationsystem.model.Recruit;
import com.example.lpp.examinationsystem.util.RestUtil;

import java.util.List;

public class RequirementAdapter extends ArrayAdapter<Recruit> {
    int resourceId;
    public RequirementAdapter(Context context, int textViewResourceId, List<Recruit> objects){
        super(context,textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Recruit xuqiu=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView project_name=(TextView) view.findViewById(R.id.xuqiu_id);
        TextView project_type=(TextView) view.findViewById(R.id.project_type);
        TextView publisher=(TextView) view.findViewById(R.id.publisher);
        TextView descript=(TextView) view.findViewById(R.id.project_descrip);
        TextView project_id=(TextView) view.findViewById(R.id.project_id);
        project_name.setText(xuqiu.getName());
        project_type.setText(xuqiu.getDescription());
        publisher.setText(RestUtil.getOwnerInfo(xuqiu).getNickname());
        descript.setText(xuqiu.getDescription());
        project_id.setText(String.valueOf(xuqiu.getId()));
        return view;
    }
}
