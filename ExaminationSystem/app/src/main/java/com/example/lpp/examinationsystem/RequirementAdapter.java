package com.example.lpp.examinationsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RequirementAdapter extends ArrayAdapter<Requirement> {
    int resourceId;
    public RequirementAdapter(Context context, int textViewResourceId, List<Requirement> objects){
        super(context,textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Requirement xuqiu=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView project_id=(TextView) view.findViewById(R.id.xuqiu_id);
        TextView publish_time=(TextView) view.findViewById(R.id.publish_time);
        TextView project_state=(TextView) view.findViewById(R.id.xuqiu_state);
        project_id.setText(xuqiu.getId());
        publish_time.setText(xuqiu.getId());
        project_state.setText(xuqiu.getState());
        return view;
    }
}
