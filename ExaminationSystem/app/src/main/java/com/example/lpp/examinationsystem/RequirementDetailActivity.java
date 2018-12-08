package com.example.lpp.examinationsystem;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequirementDetailActivity extends AppCompatActivity {

    private TextView topTab;
    private Spinner projectTypeSpinner;
    private Spinner questionTypeSpinner;
    private ImageButton addQuestionTemplate;
    private LinearLayout addPaperTemplate;
    private ImageButton delBtn;
    private ImageButton menuBtn;
    private Button outputBtn;
    private ListView listView;

    private static final String []projectType={"前端","后端","移动端"};
    private static int templateItemCount=2;
    private static final String []questionTypesText={"Java","C++","HTML","Android","iOS","JavaEE","JavaScript"};

    private List<String> projectTypes=new ArrayList<>();
    private List<String> questionTypes=new ArrayList<>();
    private List<String> menuList=new ArrayList<>();
    private Map<Integer,Integer> spinnerPositions=new HashMap<>();

    //获取的数据
    private Map<String,Integer> templateData=new HashMap<>();
    private String templateDescript;
    private int projectTypeIndex;
    private String projectDescript;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuqiu_detail);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        Intent intent=getIntent();
        String id=intent.getStringExtra("project id");
        Log.d("RequirementDetail",id);
        topTab=(TextView) findViewById(R.id.txt_top);
        topTab.setText(id);

        menuBtn=(ImageButton) findViewById(R.id.menu_btn);
        initSpinner();

        addPaperTemplate=(LinearLayout) findViewById(R.id.add_paper_template);
        addQuestionTemplate=findViewById(R.id.add_template_button);
        addQuestionTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTemplateItem();
            }
        });

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
    }
    private void initSpinner(){
        projectTypeSpinner =(Spinner) findViewById(R.id.project_type);

        projectTypes.add("前端");
        projectTypes.add("后端");
        projectTypes.add("移动端");

        questionTypes.add("Java");
        questionTypes.add("C++");
        questionTypes.add("HTML");
        questionTypes.add("Android");
        questionTypes.add("iOS");
        questionTypes.add("JavaEE");
        questionTypes.add("JavaScript");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,projectTypes);
        projectTypeSpinner.setAdapter(adapter);
        projectTypeSpinner.setSelection(2);
        projectTypeSpinner.setOnItemSelectedListener(new RequirementDetailActivity.projectTypeSpinnerListener());

        menuList.add("Save");
        menuList.add("Delete");
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,menuList);
        listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        EditText editText1=(EditText) findViewById(R.id.project_descrip);
                        projectDescript=editText1.getText().toString();
                        System.out.println(projectDescript);

                        EditText editText2=(EditText) findViewById(R.id.paper_descrip);
                        templateDescript=editText2.getText().toString();
                        System.out.println(templateDescript);

                        for (int j=0;j<addPaperTemplate.getChildCount();j++){
                            EditText editText3=(EditText) addPaperTemplate.getChildAt(j).findViewById(R.id.question_count);
                            Spinner question_spinner=(Spinner) addPaperTemplate.getChildAt(j).findViewById(R.id.queston_type);
                            if (question_spinner.isSelected()&&editText3.getText().toString().length()>0)
                            templateData.put(questionTypesText[question_spinner.getSelectedItemPosition()],Integer.parseInt(editText3.getText().toString()));
                        }
                        System.out.println(templateData);

                        projectTypeIndex=projectTypeSpinner.getSelectedItemPosition();

                        Toast.makeText(RequirementDetailActivity.this,"Successfully save",Toast.LENGTH_SHORT).show();
                        listView.setVisibility(View.GONE);
                        break;
                    case 1:
                        Toast.makeText(RequirementDetailActivity.this,"Successfully delete",Toast.LENGTH_SHORT).show();
                        listView.setVisibility(View.GONE);
                        break;
                        default:
                            break;
                }
            }
        });
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
    //parent.getTag()可以获取试题类型spinner中具体的tag
    class questionTypeSpinnerListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            //将选择的元素显示出来
            String selected = parent.getItemAtPosition(position).toString();
            Log.d("questionType",parent.getTag()+" "+String.valueOf(position));
            spinnerPositions.put(Integer.parseInt(parent.getTag().toString()),position);
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
        questionTypeSpinner=(Spinner) newItemView.findViewById(R.id.queston_type);
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
    }
    private void delTemplateItem(View view){
        addPaperTemplate.removeView((View) view.getParent());
    }
}
