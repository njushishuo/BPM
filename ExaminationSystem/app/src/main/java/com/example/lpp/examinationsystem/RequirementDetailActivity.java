package com.example.lpp.examinationsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpp.examinationsystem.model.Label;
import com.example.lpp.examinationsystem.model.LabelList;
import com.example.lpp.examinationsystem.model.Template;
import com.example.lpp.examinationsystem.model.TemplateItem;
import com.example.lpp.examinationsystem.rest.LabelDAO;
import com.example.lpp.examinationsystem.rest.PaperDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequirementDetailActivity extends AppCompatActivity {

    private TextView topTab;
    private EditText editProjectDes;
    private EditText editPaperDes;
    private EditText editCount;
    private Spinner projectTypeSpinner;
    private Spinner questionTypeSpinner;
    private ImageButton addQuestionTemplate;
    private LinearLayout addPaperTemplate;
    private ImageButton delBtn;
    private ImageButton menuBtn;
    private Button outputBtn;
    private ListView listView;

    private static final String []projectType={"前端","后端","移动端","WEB"};
    private static int templateItemCount=0;
    private static final int UPDATE_LABEL=1;
    private static final int SAVE=2;
    private List<String> questionTypes=new ArrayList<>();
    private List<String> menuList=new ArrayList<>();
    private Map<Integer,Integer> spinnerPositions=new HashMap<>();

    //获取的数据
    private Map<String,Integer> templateData=new HashMap<>();
    private String templateDescript;
    private String id;
    private String name;
    private String type;
    private int projectTypeIndex=0;
    private String des;
    private List<Template> templateList;
    private List<Label> labelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuqiu_detail);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        //获取xuqiu_fragment传来的数据
        Intent intent=getIntent();
        id= intent.getStringExtra("project id");
        name=intent.getStringExtra("project name");
        type=intent.getStringExtra("project type");
        des=intent.getStringExtra("project description");
        templateList=(List<Template>) intent.getSerializableExtra("paper frame");
        Template template=templateList.get(0);
//        Log.d("Paper Frame",template.toString());

        topTab=(TextView) findViewById(R.id.txt_top);
        topTab.setText(name);
        menuBtn=(ImageButton) findViewById(R.id.menu_btn);
        editProjectDes=(EditText) findViewById(R.id.project_descrip);
        editProjectDes.setText(des);
        editPaperDes=(EditText) findViewById(R.id.paper_descrip);
        editPaperDes.setText(template.getDescription());
        addPaperTemplate=(LinearLayout) findViewById(R.id.add_paper_template);
        addQuestionTemplate=findViewById(R.id.add_template_button);
        new Thread(new Runnable() {
            @Override
            public void run() {
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
        addQuestionTemplate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addTemplateItem();
                }
        });
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_LABEL:
                    initSpinner();
                    initTemplate();
                    initListView();
                    listView.setVisibility(View.GONE);
                    menuBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (listView.getVisibility()==View.GONE){
                                listView.setVisibility(View.VISIBLE);
                                listView.bringToFront();
                            }else{
                                listView.setVisibility(View.GONE);
                            }
                        }
                    });
                    break;
                default:break;
            }
        }
    };
    private void initListView(){
        //顶栏菜单弹出列表
        menuList.add("保存");
        menuList.add("删除");
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,menuList);
        listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0://保存修改
                        des=editProjectDes.getText().toString();
//                        System.out.println(des);
                        templateDescript=editPaperDes.getText().toString();
//                        System.out.println(templateDescript);
                        for (int j=0;j<addPaperTemplate.getChildCount();j++){
                            EditText editText3=(EditText) addPaperTemplate.getChildAt(j).findViewById(R.id.question_count);
                            Spinner question_spinner=(Spinner) addPaperTemplate.getChildAt(j).findViewById(R.id.question_type);
                            if (question_spinner.getSelectedItemPosition()!=labelList.size()&&editText3.getText().toString().length()>0)
                                templateData.put(questionTypes.get(question_spinner.getSelectedItemPosition()).toString(),Integer.parseInt(editText3.getText().toString()));
                        }
//                        System.out.println(templateData);
                        projectTypeIndex=projectTypeSpinner.getSelectedItemPosition();
//                        System.out.println(projectTypeIndex);
                        Toast.makeText(RequirementDetailActivity.this,"Successfully save",Toast.LENGTH_SHORT).show();
                        listView.setVisibility(View.GONE);
                        break;
                    case 1://删除项目
                        Toast.makeText(RequirementDetailActivity.this,"Successfully delete",Toast.LENGTH_SHORT).show();
                        listView.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void initSpinner(){
        projectTypeSpinner =(Spinner) findViewById(R.id.project_type);
        questionTypes.add("请选择");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,projectType);
        projectTypeSpinner.setAdapter(adapter);
        for (int i=0;i<4;i++)
            if (type.equals(projectType[i]))
                projectTypeIndex=i;
        projectTypeSpinner.setSelection(projectTypeIndex);
        projectTypeSpinner.setOnItemSelectedListener(new RequirementDetailActivity.projectTypeSpinnerListener());
    }

    private void initTemplate(){
        List<TemplateItem> templateItems=new ArrayList<>();
        templateItems=templateList.get(0).getItems();
        templateItemCount=templateItems.size();
        for (int i=0;i<templateItemCount;i++){
            View newItemView=View.inflate(this,R.layout.item_add_template,null);
            questionTypeSpinner=(Spinner) newItemView.findViewById(R.id.question_type);
            RequirementDetailActivity.Myadapter adapter2=new RequirementDetailActivity.Myadapter(this,android.R.layout.simple_spinner_dropdown_item,questionTypes);
            questionTypeSpinner.setAdapter(adapter2);
            int index=0;
            for (int j=0;j<questionTypes.size();j++){
                if (questionTypes.get(j).equals(templateItems.get(i).getLabel().getName()))
                    index=j;
            }
            questionTypeSpinner.setSelection(index,true);
            questionTypeSpinner.setOnItemSelectedListener(new RequirementDetailActivity.questionTypeSpinnerListener());
            delBtn=(ImageButton) newItemView.findViewById(R.id.del_btn);
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delTemplateItem(view);
                }
            });
            editCount=(EditText) newItemView.findViewById(R.id.question_count);
//            System.out.println(editCount);
            editCount.setText(String.valueOf(templateItems.get(i).getCount()));
            addPaperTemplate.addView(newItemView);
        }
    }
    /**
     * 定义一个Myadapter类继承ArrayAdapter
     * 重写以下两个方法
     * */
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

    //获取项目类型
    class projectTypeSpinnerListener implements AdapterView.OnItemSelectedListener{
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

    private void addTemplateItem(){
        if (addPaperTemplate.getChildCount()==7){
            return;
        }
        View newItemView=View.inflate(this,R.layout.item_add_template,null);
        questionTypeSpinner=(Spinner) newItemView.findViewById(R.id.question_type);
        RequirementDetailActivity.Myadapter adapter2=new RequirementDetailActivity.Myadapter(this,android.R.layout.simple_spinner_dropdown_item,questionTypes);
        questionTypeSpinner.setAdapter(adapter2);
        questionTypeSpinner.setSelection(questionTypes.size()-1,true);
        questionTypeSpinner.setOnItemSelectedListener(new RequirementDetailActivity.questionTypeSpinnerListener());
        delBtn=(ImageButton) newItemView.findViewById(R.id.del_btn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delTemplateItem(view);
            }
        });
        addPaperTemplate.addView(newItemView);
        templateItemCount++;
    }
    private void delTemplateItem(View view){
        addPaperTemplate.removeView((View) view.getParent());
    }
}
