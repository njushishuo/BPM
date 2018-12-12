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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lpp.examinationsystem.model.Label;
import com.example.lpp.examinationsystem.rest.LabelDAO;
import com.example.lpp.examinationsystem.rest.PaperDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRequirementActivity extends AppCompatActivity {

    private Spinner projectTypeSpinner;
    private Spinner questionTypeSpinner;
    private ImageButton addQuestionTemplate;
    private LinearLayout addPaperTemplate;
    private ImageButton delBtn;
    private Button add;

    private List<String> projectTypes=new ArrayList<>();
    private List<String> questionTypes=new ArrayList<>();
    //获取的数据
    private Map<String,Integer> templateData=new HashMap<>();
    private String templateDescript;
    private int projectTypeIndex;
    private String projectDescript;
    private final static int UPDATE_LABEL=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_xuqiu);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                LabelDAO labelDAO=new LabelDAO();
                List<Label> labelList=(ArrayList)labelDAO.getList();
                for (int i=0;i<labelList.size();i++){
                    questionTypes.add(labelList.get(i).getName().toString());
                }
                Message message=new Message();
                message.what=UPDATE_LABEL;
                handler.sendMessage(message);
            }
        }).start();

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

                for (int i=0;i<addPaperTemplate.getChildCount();i++){
                    EditText editText3=(EditText) addPaperTemplate.getChildAt(i).findViewById(R.id.question_count);
                    Spinner question_spinner=(Spinner) addPaperTemplate.getChildAt(i).findViewById(R.id.question_type);
                    if (question_spinner.isSelected()&&editText3.getText().toString().length()>0)
                        templateData.put(questionTypes.get(question_spinner.getSelectedItemPosition()),Integer.parseInt(editText3.getText().toString()));
                }
                System.out.println(templateData);
                Toast.makeText(AddRequirementActivity.this,"Success",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddRequirementActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_LABEL:
                    initSpinner();
                    break;
                default:break;
            }
        }
    };
    private void initSpinner(){
        projectTypeSpinner =(Spinner) findViewById(R.id.project_type);

        projectTypes.add("前端");
        projectTypes.add("后端");
        projectTypes.add("移动端");
        projectTypes.add("WEB");
        projectTypes.add("请选择");

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
        Myadapter adapter2=new Myadapter(this,android.R.layout.simple_spinner_dropdown_item,questionTypes);
        questionTypeSpinner.setAdapter(adapter2);
        questionTypeSpinner.setSelection(questionTypes.size()-1,true);
        questionTypeSpinner.setOnItemSelectedListener(new questionTypeSpinnerListener());
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
        View parent=(View) view.getParent();
        addPaperTemplate.removeView(parent);
    }

}
