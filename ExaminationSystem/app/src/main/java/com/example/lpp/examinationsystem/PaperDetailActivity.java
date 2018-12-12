package com.example.lpp.examinationsystem;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lpp.examinationsystem.model.Paper;
import com.example.lpp.examinationsystem.model.Question;
import com.example.lpp.examinationsystem.model.QuestionList;
import com.example.lpp.examinationsystem.rest.PaperDAO;
import com.example.lpp.examinationsystem.rest.QuestionDAO;
import com.example.lpp.examinationsystem.util.RestUtil;

import java.util.ArrayList;
import java.util.List;

public class PaperDetailActivity extends AppCompatActivity {
    TextView tabText;
    List<Question> question_list=new ArrayList<>();
    private final static int UPDATE_LIST=0;
    private String id;
    private QuestionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_detail);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        tabText=(TextView) findViewById(R.id.txt_top);
        Intent intent=getIntent();
        id=intent.getStringExtra("paper id");
//        System.out.println(id);
        tabText.setText(id);
        //初始化试题列表数据
        initQustionData();
        adapter=new QuestionAdapter(PaperDetailActivity.this,R.layout.question_item,question_list);
        ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
    private void initQustionData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                PaperDAO paperDAO=new PaperDAO();
                Paper paper=paperDAO.getObject(id);
                List<Question> questionList=RestUtil.getQuestionsInfo(paper);
                for (int i=0;i<questionList.size();i++){
                    Question question=new Question();
                    question=questionList.get(i);
                    question_list.add(question);
                }
                Message message=new Message();
                message.what=UPDATE_LIST;
                handler.sendMessage(message);
            }
        }).start();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_LIST:
                    adapter.notifyDataSetChanged();
                    break;
                default:break;
            }
        }
    };
}
