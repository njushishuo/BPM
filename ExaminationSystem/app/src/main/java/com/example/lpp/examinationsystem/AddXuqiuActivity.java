package com.example.lpp.examinationsystem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddXuqiuActivity extends AppCompatActivity {

    private Spinner projectTypeSpinner;
    private Spinner questionTypeSpinner;
    private ImageButton addQuestionTemplate;
    private LinearLayout addPaperTemplate;
    private ImageButton delBtn;
    private Button add;

    private static final String []projectType={"前端","后端","移动端"};
    private static int templateItemCount=0;
    private static final String []questionTypesText={"Java","C++","HTML","Android","iOS","JavaEE","JavaScript"};

    private List<String> projectTypes=new ArrayList<>();
    private List<String> questionTypes=new ArrayList<>();

    private Map<Integer,Integer> spinnerPositions=new HashMap<>();
    //获取的数据
    private Map<String,Integer> templateData=new HashMap<>();
    private String templateDescript;
    private int projectTypeIndex;
    private String projectDescript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_xuqiu);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        initSpinner();

        addPaperTemplate=(LinearLayout) findViewById(R.id.add_paper_template);
        addQuestionTemplate=findViewById(R.id.add_template_button);
        addQuestionTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTemplateItem();
            }
        });

        add=(Button) findViewById(R.id.add_xuqiu_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText1=(EditText) findViewById(R.id.project_descrip);
                projectDescript=editText1.getText().toString();
                System.out.println(projectDescript);

                EditText editText2=(EditText) findViewById(R.id.paper_descrip);
                templateDescript=editText2.getText().toString();
                System.out.println(templateDescript);

                for (int i=0;i<templateItemCount;i++){
                    EditText editText3=(EditText) addPaperTemplate.getChildAt(i).findViewById(R.id.question_count);
                    templateData.put(questionTypesText[spinnerPositions.get(i)],Integer.parseInt(editText3.getText().toString()));
                }

                System.out.println(templateData);
                Toast.makeText(AddXuqiuActivity.this,"Success",Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(AddXuqiuActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initSpinner(){
        projectTypeSpinner =(Spinner) findViewById(R.id.project_type);

        projectTypes.add("前端");
        projectTypes.add("后端");
        projectTypes.add("移动端");
        projectTypes.add("请选择");

        questionTypes.add("Java");
        questionTypes.add("C++");
        questionTypes.add("HTML");
        questionTypes.add("Android");
        questionTypes.add("iOS");
        questionTypes.add("JavaEE");
        questionTypes.add("JavaScript");
        questionTypes.add("请选择");

        Myadapter adapter=new Myadapter(this,android.R.layout.simple_spinner_dropdown_item,projectTypes);
        projectTypeSpinner.setAdapter(adapter);
        projectTypeSpinner.setSelection(projectTypes.size()-1,true);
        projectTypeSpinner.setOnItemSelectedListener(new projectTypeSpinnerListener());

    }

    /**
     * 定义一个Myadapter类继承ArrayAdapter
     * 重写以下两个方法
     * */
    class Myadapter<T> extends ArrayAdapter{
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
            Log.d("project type",String.valueOf(position));
            projectTypeIndex=position;
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
        if (templateItemCount==7){
            return;
        }
        View newItemView=View.inflate(this,R.layout.item_add_template,null);
        newItemView.setTag(templateItemCount);
        questionTypeSpinner=(Spinner) newItemView.findViewById(R.id.queston_type);
        Myadapter adapter2=new Myadapter(this,android.R.layout.simple_spinner_dropdown_item,questionTypes);
        questionTypeSpinner.setTag(templateItemCount);
        questionTypeSpinner.setAdapter(adapter2);
        questionTypeSpinner.setSelection(questionTypes.size()-1,true);
        questionTypeSpinner.setOnItemSelectedListener(new questionTypeSpinnerListener());
        delBtn=(ImageButton) newItemView.findViewById(R.id.del_btn);
        delBtn.setTag(templateItemCount);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delTemplateItem(view);
            }
        });
        templateItemCount++;
        addPaperTemplate.addView(newItemView);
    }
    private void delTemplateItem(View view){
        Log.d("delTemplateItem",view.getTag().toString());
        int del_index=Integer.parseInt(view.getTag().toString());
        addPaperTemplate.removeViewAt(del_index);
        if (del_index==templateItemCount-1){
            if (spinnerPositions.containsKey(del_index)){
                spinnerPositions.remove(del_index);
            }
            System.out.println("spinner Position"+spinnerPositions);
            templateItemCount--;
            return;
        }else{
            for (int i=del_index;i<templateItemCount-1;i++){
                if (spinnerPositions.containsKey(i+1)){
                    spinnerPositions.put(i,spinnerPositions.get(i+1));
                }
                addPaperTemplate.getChildAt(i).setTag(i);
                System.out.println("spinner Position"+spinnerPositions);
            }
            if (spinnerPositions.containsKey(templateItemCount-1)){
                spinnerPositions.remove(--templateItemCount);
            }
        }
    }
}
