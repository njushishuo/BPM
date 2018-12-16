package com.example.lpp.examinationsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lpp.examinationsystem.model.Label;
import com.example.lpp.examinationsystem.model.Template;
import com.example.lpp.examinationsystem.rest.LabelDAO;
import com.example.lpp.examinationsystem.rest.RecruitDAO;
import com.example.lpp.examinationsystem.rest.TemplateDAO;
import com.example.lpp.examinationsystem.util.RestUtil;
import com.example.lpp.examinationsystem.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddTemplateActivity extends AppCompatActivity {

    private Spinner questionTypeSpinner;
    private ImageButton addTemplateItem;
    private LinearLayout paperTemplate;
    private EditText editPaperDes;
    private Button addTemplate;
    private ImageButton delBtn;

    private List<String> questionTypes=new ArrayList<>();
    private static final int UPDATE_LABEL=1;
    private static final int ADD_TEMPLATE_SUCCESS=2;
    private static final int ADD_TEMPLATE_FAIL=3;
    private String templateDescript;
    private List<Label> labelList;
    private int addInvalidLabel=1;//0:不添加请选择；1：添加请选择
    private Map<Label,Integer> templateItemsMap=new HashMap<>();
    private String recruitId;
    private String type;
    private String recruitName;
    private String des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        editPaperDes=(EditText) findViewById(R.id.paper_descrip);
        addTemplateItem=(ImageButton) findViewById(R.id.add_template_item);
        addTemplate=(Button) findViewById(R.id.add_template);
        paperTemplate=findViewById(R.id.template_items);
        final Intent intent=getIntent();
        recruitId=intent.getStringExtra("recruitId");
        type=intent.getStringExtra("project type");
        recruitName=intent.getStringExtra("project name");
        des=intent.getStringExtra("project description");
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
                handler.sendMessage(message);
            }
        }).start();
        addTemplateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTemplateItem();
            }
        });
        addTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                templateDescript=editPaperDes.getText().toString();
                if (templateDescript.length()>0&&paperTemplate!=null){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i=0;i<paperTemplate.getChildCount();i++){
                                EditText itemCount=(EditText) paperTemplate.getChildAt(i).findViewById(R.id.question_count);
                                Spinner itemSpinner=(Spinner) paperTemplate.getChildAt(i).findViewById(R.id.question_type);
                                if (itemCount.length()>0&&itemSpinner.getSelectedItemPosition()!=labelList.size()){
//                                    System.out.println(labelList.get(itemSpinner.getSelectedItemPosition()).getName()+" "+itemCount.getText().toString());
                                    templateItemsMap.put(labelList.get(itemSpinner.getSelectedItemPosition()),Integer.parseInt(itemCount.getText().toString()));
                                }
                            }
                            TemplateDAO templateDAO=new TemplateDAO();
                            Template template=new Template();
                            template.setRecruitId(recruitId);
                            template.setName(templateDescript);
                            template.setItems(StringUtil.buildTemplateItems(templateItemsMap));
                            if (templateDAO.postObject(template)==null){
                                Message message=new Message();
                                message.what=ADD_TEMPLATE_FAIL;
                                handler.sendMessage(message);
                            }else {
                                Message message=new Message();
                                message.what=ADD_TEMPLATE_SUCCESS;
                                handler.sendMessage(message);
                            }
                        }
                    }).start();
                }else{
                    Toast.makeText(AddTemplateActivity.this,"请填写试卷描述和正确设置模板",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_LABEL:
                    break;
                case ADD_TEMPLATE_SUCCESS:
                    addTemplateSuccess();
                    break;
                case ADD_TEMPLATE_FAIL:
                    addTemplateFail();
                    break;
                default:break;
            }
        }
    };

    private void addTemplateItem(){
        if (paperTemplate.getChildCount()==7){
            return;
        }
        if (addInvalidLabel==1){
            questionTypes.add("请选择");
            addInvalidLabel=0;
        }

        View newItemView=View.inflate(this,R.layout.item_add_template,null);
        questionTypeSpinner=(Spinner) newItemView.findViewById(R.id.question_type);
        AddTemplateActivity.Myadapter adapter2=new AddTemplateActivity.Myadapter(this,android.R.layout.simple_spinner_dropdown_item,questionTypes);
        questionTypeSpinner.setAdapter(adapter2);
        questionTypeSpinner.setSelection(questionTypes.size()-1,true);
        questionTypeSpinner.setOnItemSelectedListener(new AddTemplateActivity.questionTypeSpinnerListener());
        delBtn=(ImageButton) newItemView.findViewById(R.id.del_btn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delTemplateItem(view);
            }
        });
        paperTemplate.addView(newItemView);
    }
    private void delTemplateItem(View view){
        paperTemplate.removeView((View) view.getParent());
    }
    //获取试题类型
    class questionTypeSpinnerListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            //将选择的元素显示出来
            String selected = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            System.out.println("Nothing Select");
        }
    }
    class Myadapter<T> extends ArrayAdapter {
        public Myadapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
            super(context, resource, objects);
        }

        @Override
        public int getCount() {
            //返回数据的统计数量，大于0项则减去1项，从而不显示最后一项
            int i = super.getCount();
            return i>0?i-1:i;
        }
    }
    void addTemplateSuccess(){
        Toast.makeText(AddTemplateActivity.this,"添加模板成功",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(AddTemplateActivity.this,RequirementDetailActivity.class);
        intent.putExtra("project type",type);
        intent.putExtra("project id",recruitId);
        intent.putExtra("project description",des);
        intent.putExtra("project name",recruitName);
        setResult(RESULT_OK,intent);
        finish();
    }
    void addTemplateFail(){
        Toast.makeText(AddTemplateActivity.this,"添加模板失败，请稍后重试",Toast.LENGTH_SHORT).show();
    }
}

