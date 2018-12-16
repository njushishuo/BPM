package com.example.lpp.examinationsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.lpp.examinationsystem.model.Recruit;
import com.example.lpp.examinationsystem.model.Template;
import com.example.lpp.examinationsystem.rest.LabelDAO;
import com.example.lpp.examinationsystem.rest.QuestionDAO;
import com.example.lpp.examinationsystem.rest.RecruitDAO;
import com.example.lpp.examinationsystem.rest.TemplateDAO;
import com.example.lpp.examinationsystem.util.RestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequirementDetailActivity extends AppCompatActivity {

    private TextView topTab;
    private EditText editProjectDes;
    private Spinner projectTypeSpinner;
    private ImageButton menuBtn;
    private Button addTemplate;
    private ListView listView;
    private ListView templateListView;
    private static final String []projectType={"WEB","APP","WE_CHAT_APPLET"};
    private static final int UPDATE_TEMPLATE_LIST=0;
    private static final int SAVE_SUCCESS=1;
    private static final int SAVE_FAIL=2;
    private List<String> menuList=new ArrayList<>();
    private List<Template> templateList;
    //获取的数据
    private String id;
    private String name;
    private String type;
    private int projectTypeIndex=0;
    private String des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuqiu_detail);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }

        //顶栏menu
        menuBtn=(ImageButton) findViewById(R.id.menu_btn);
        //顶栏menu弹出列表
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取xuqiu_fragment传来的数据
        final Intent intent=getIntent();
        des=intent.getStringExtra("project description");
        id= intent.getStringExtra("project id");
        name=intent.getStringExtra("project name");
        type=intent.getStringExtra("project type");
        //标题栏名字
        topTab=(TextView) findViewById(R.id.txt_top);
        topTab.setText(name);
        editProjectDes=(EditText) findViewById(R.id.project_descrip);
        editProjectDes.setText(des);
        //初始化project的spinner
        initSpinner();
        //初始化template列表
        initTemplate();
        //添加模板button
        addTemplate=(Button) findViewById(R.id.add_template);
        addTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(RequirementDetailActivity.this,AddTemplateActivity.class);
                intent1.putExtra("recruitId",id);
                intent1.putExtra("project type",type);
                intent1.putExtra("project description",des);
                intent1.putExtra("project name",name);
                startActivityForResult(intent1,1);
            }
        });
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TEMPLATE_LIST:
                    List<String>  templateListText=new ArrayList<>();
                    for (int i=0;i<templateList.size();i++){
                        templateListText.add(templateList.get(i).getName());
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(RequirementDetailActivity.this,android.R.layout.simple_list_item_1,templateListText);
                    templateListView.setAdapter(adapter);
                    int totalHeight = 0;
                    for (int i = 0; i < adapter.getCount(); i++) {
                        View listItem = adapter.getView(i, null, listView);
                        listItem.measure(0, 0);
                        totalHeight += listItem.getMeasuredHeight();
                    }

                    ViewGroup.LayoutParams params =templateListView.getLayoutParams();
                    params.height = totalHeight + (templateListView.getDividerHeight() * (adapter.getCount()-1));
                    ((ViewGroup.MarginLayoutParams)params).setMargins(10, 10, 10, 10);
                    templateListView.setLayoutParams(params);
                    break;
                case SAVE_SUCCESS:
                    Toast.makeText(RequirementDetailActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                    listView.setVisibility(View.GONE);
                    break;
                case SAVE_FAIL:
                    Toast.makeText(RequirementDetailActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                    listView.setVisibility(View.GONE);
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
                        projectTypeIndex=projectTypeSpinner.getSelectedItemPosition();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                RecruitDAO recruitDAO=new RecruitDAO();
                                Recruit newRecruit=new Recruit();
                                newRecruit=recruitDAO.getObject(id);
                                newRecruit.setType(projectType[projectTypeIndex]);
                                newRecruit.setDescription(des);
                                Recruit res=recruitDAO.putObject(newRecruit);
                                if (res==null){
                                    Message message=new Message();
                                    message.what=SAVE_FAIL;
                                    handler.sendMessage(message);
                                }else{
                                    Message message=new Message();
                                    message.what=SAVE_SUCCESS;
                                    handler.sendMessage(message);
                                }
                            }
                        }).start();

                        break;
                    case 1://删除项目
                        Toast.makeText(RequirementDetailActivity.this,"暂无删除项目功能",Toast.LENGTH_SHORT).show();
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
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,projectType);
        projectTypeSpinner.setAdapter(adapter);
        for (int i=0;i<3;i++)
            if (type.equals(projectType[i]))
                projectTypeIndex=i;
        projectTypeSpinner.setSelection(projectTypeIndex);
        projectTypeSpinner.setOnItemSelectedListener(new RequirementDetailActivity.projectTypeSpinnerListener());
    }

    private void initTemplate(){
        templateListView=(ListView) findViewById(R.id.template_list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                TemplateDAO templateDAO=new TemplateDAO();
                RecruitDAO recruitDao=new RecruitDAO();
                if (templateList!=null) templateList.removeAll(templateList);
                templateList=RestUtil.getTemplatesByRecruit(recruitDao.getObject(id));
                if (templateList!=null){
                    Message message=new Message();
                    message.what=UPDATE_TEMPLATE_LIST;
                    handler.sendMessage(message);
                }
            }
        }).start();
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


}
