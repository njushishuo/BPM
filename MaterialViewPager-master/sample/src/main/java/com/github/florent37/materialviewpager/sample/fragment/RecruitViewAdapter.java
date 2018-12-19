package com.github.florent37.materialviewpager.sample.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.model.Recruit;

import java.util.ArrayList;
import java.util.List;

public class RecruitViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Recruit> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public RecruitViewAdapter() {
        this.contents = new ArrayList<>();
    }

    public RecruitViewAdapter(List<Recruit> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
//            case 0:
//                return TYPE_CELL;
            default:
                return TYPE_HEADER;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_recruit, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                View view = holder.itemView;
                Recruit recruit = contents.get(position);
                TextView project_name = (TextView) view.findViewById(R.id.xuqiu_id);
                TextView project_type = (TextView) view.findViewById(R.id.project_type);
                TextView publisher = (TextView) view.findViewById(R.id.publisher);
                TextView descript = (TextView) view.findViewById(R.id.project_descrip);
                TextView project_id = (TextView) view.findViewById(R.id.project_id);
                project_name.setText(recruit.getName());
                project_type.setText(recruit.getType());
                publisher.setText(recruit.getOwnerNickname());
                descript.setText("    " + recruit.getDescription());
                project_id.setText(String.valueOf(recruit.getId()));
                break;
            case TYPE_CELL:
                break;
        }
    }
}