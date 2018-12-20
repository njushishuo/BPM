package com.github.florent37.materialviewpager.sample.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.model.PaperVO;

import java.util.ArrayList;
import java.util.List;

public class PaperViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<PaperVO> papers;

    static final int TYPE_LARGE = 0;

    public PaperViewAdapter() {
        this.papers = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_LARGE;
    }

    @Override
    public int getItemCount() {
        return papers.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_LARGE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_paper, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_LARGE:
                View view = holder.itemView;
                PaperVO paper = papers.get(position);

                TextView paper_name = (TextView) view.findViewById(R.id.paper_name);
                paper_name.setText(paper.getName());
                TextView project_name = (TextView) view.findViewById(R.id.project_name);
                project_name.setText(paper.getRecruit().getName());
                TextView questions_text = (TextView) view.findViewById(R.id.questions);
                questions_text.setText(paper.analysisInfo());
                break;
        }
    }
}