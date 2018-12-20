package com.github.florent37.materialviewpager.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.florent37.materialviewpager.sample.model.Question;

import java.util.List;

public class QuestionAdapter extends ArrayAdapter<Question> {
    private int resourceId;
    public QuestionAdapter(Context context, int textViewResourcedId, List<Question> objects){
        super(context,textViewResourcedId,objects);
        resourceId=textViewResourcedId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Question question=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.index=(TextView) view.findViewById(R.id.index);
            viewHolder.question_id=(TextView) view.findViewById(R.id.question_id);
            viewHolder.question_descript=(TextView) view.findViewById(R.id.question_descript);
            viewHolder.question_type=(TextView) view.findViewById(R.id.question_type);
            viewHolder.question_right_answer=(TextView) view.findViewById(R.id.question_right_asnswer);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.index.setText("试题"+String.valueOf(position+1)+"：");
        viewHolder.question_id.setText(String.valueOf(question.getId()));
        viewHolder.question_descript.setText("  "+question.getDescription());
        viewHolder.question_type.setText(question.getQuestionType());
        viewHolder.question_right_answer.setText(question.getAnswer());
        return view;
    }

    class ViewHolder{
        TextView index;
        TextView question_id;
        TextView question_descript;
        TextView question_type;
        TextView question_right_answer;
    }
}
