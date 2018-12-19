package com.github.florent37.materialviewpager.sample.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.model.Paper;
import com.github.florent37.materialviewpager.sample.model.Question;
import com.github.florent37.materialviewpager.sample.model.Recruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaperViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Paper> papers;
    Map<Paper, Pair<Recruit, List<Question>>> contents;

    static final int TYPE_LARGE = 0;

    public PaperViewAdapter() {
        this.papers = new ArrayList<>();
        this.contents = new HashMap<>();
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
                Paper paper = papers.get(position);
                Pair<Recruit, List<Question>> pair = contents.get(paper);
                Recruit recruit = pair.first;
                List<Question> questions = pair.second;

                StringBuilder builder = new StringBuilder();
                for (Question q : questions) {
                    for (int i = 0; i < 20; i++) {
                        builder.append(i + " ").append(q.getDescription()).append("\n");
                    }
                }

                TextView paper_name = (TextView) view.findViewById(R.id.paper_name);
                paper_name.setText(paper.getName());
                TextView project_name = (TextView) view.findViewById(R.id.project_name);
                project_name.setText(recruit.getName());
                TextView questions_text = (TextView) view.findViewById(R.id.questions);
                questions_text.setMovementMethod(ScrollingMovementMethod.getInstance());
                questions_text.setText(builder.toString());
                break;
        }
    }
}