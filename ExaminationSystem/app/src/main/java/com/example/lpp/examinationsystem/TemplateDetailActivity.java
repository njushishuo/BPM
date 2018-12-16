package com.example.lpp.examinationsystem;

import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.lpp.examinationsystem.model.Label;
import com.example.lpp.examinationsystem.rest.LabelDAO;
import com.example.lpp.examinationsystem.rest.RecruitDAO;
import com.example.lpp.examinationsystem.util.RestUtil;

import java.util.ArrayList;
import java.util.List;

public class TemplateDetailActivity extends AppCompatActivity {
    private Spinner questionTypeSpinner;
    private ImageButton addTemplateItem;
    private LinearLayout paperTemplate;
    private EditText editPaperDes;
    private Button addTemplate;

    private List<String> questionTypes=new ArrayList<>();
    private static final int UPDATE_LABEL=1;
    private String templateDescript;
    private List<Label> labelList;
    private int addInvalidLabel=1;//0:不添加请选择；1：添加请选择
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_detail);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取标签
                LabelDAO labelDAO=new LabelDAO();
                labelList=new ArrayList<>();
                labelList=(ArrayList) labelDAO.getList();
                for (int i=0;i<labelList.size();i++){
                    questionTypes.add(labelList.get(i).getName().toString());
                }
                Message message=new Message();
                message.what=UPDATE_LABEL;
//                handler.sendMessage(message);
            }
        }).start();
//        addQuestionTemplate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addTemplateItem();
//            }
//        });
    }
}
