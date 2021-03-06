package com.github.florent37.materialviewpager.sample.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.model.Recruit;

import java.util.ArrayList;
import java.util.List;

public class RecruitViewAdapter extends RecyclerView.Adapter<RecruitViewHolder> {

    List<Recruit> contents;

    static final int TYPE_LARGE = 0;

    private OnClickRecruitItemRecyclerListener listener;

    public RecruitViewAdapter() {
        this.contents = new ArrayList<>();
    }

    public RecruitViewAdapter(List<Recruit> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_LARGE;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_LARGE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_recruit, parent, false);
                final RecruitViewHolder viewHolder=new RecruitViewHolder(view);
                if (listener!=null){
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.onRecruitItemClick(view,viewHolder.getLayoutPosition());
                        }
                    });
                }
                return viewHolder;
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecruitViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_LARGE:
                View view = holder.itemView;
                Recruit recruit = contents.get(position);
                TextView project_name = (TextView) view.findViewById(R.id.xuqiu_id);
                TextView project_type = (TextView) view.findViewById(R.id.project_type);
                TextView publisher = (TextView) view.findViewById(R.id.publisher);
                TextView descript = (TextView) view.findViewById(R.id.project_descrip);
                TextView project_id = (TextView) view.findViewById(R.id.project_id);
                project_name.setText(recruit.getName());
                switch (recruit.getType()) {
                    case "WEB":
                        project_type.setText("网页开发");
                        break;
                    case "APP":
                        project_type.setText("移动开发");
                        break;
                    default:
                        project_type.setText("小程序开发");
                        break;
                }
                publisher.setText(recruit.getOwnerNickname());
                descript.setText("    " + recruit.getDescription());
                project_id.setText(String.valueOf(recruit.getId()));
                ImageView image = (ImageView) view.findViewById(R.id.image);
                image.setBackgroundResource(getCircleRes(recruit));
                break;
        }
    }

    public int getCircleRes(Recruit recruit) {
        if (recruit.getType().equals("WEB")) {
            return R.drawable.recruit_type1;
        } else if (recruit.getType().equals("APP")) {
            return R.drawable.recruit_type2;
        } else {
            return R.drawable.recruit_type3;
        }
    }

    public void setListener(OnClickRecruitItemRecyclerListener listener){
        this.listener=listener;
    }
}