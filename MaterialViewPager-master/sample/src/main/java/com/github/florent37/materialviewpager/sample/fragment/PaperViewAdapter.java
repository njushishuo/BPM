package com.github.florent37.materialviewpager.sample.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.model.Paper;
import com.github.florent37.materialviewpager.sample.model.PaperVO;

import java.util.ArrayList;
import java.util.List;

public class PaperViewAdapter extends RecyclerView.Adapter<PaperViewHolder> {

    List<PaperVO> papers;

    static final int TYPE_LARGE = 0;

    private OnClickPaperItemRecyclerListener listener;

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
    public PaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_LARGE: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_paper, parent, false);
                final PaperViewHolder viewHolder=new PaperViewHolder(view);
//                return new RecyclerView.ViewHolder(view) {
//                };
                if (listener!=null){
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.onPaperItemClick(view,viewHolder.getLayoutPosition());
                        }
                    });
                }
                return viewHolder;
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(PaperViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_LARGE:
                View view = holder.itemView;
                PaperVO paper = papers.get(position);

                TextView paper_name = (TextView) view.findViewById(R.id.paper_name);
                paper_name.setText(paper.getName());
                TextView project_name = (TextView) view.findViewById(R.id.project_name);
                project_name.setText(paper.getRecruit().getName());
                TextView questions_text = (TextView) view.findViewById(R.id.questions);
//                questions_text.setText(paper.analysisInfo());
                questions_text.setText(" ");
                break;
        }
    }

    public void setListener(OnClickPaperItemRecyclerListener listener){
        this.listener=listener;
    }

}