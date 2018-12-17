package com.example.lpp.examinationsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.NumberKeyListener;
import android.view.View;
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
import com.example.lpp.examinationsystem.rest.LabelDAO;
import com.example.lpp.examinationsystem.rest.RecruitDAO;
import com.example.lpp.examinationsystem.rest.TemplateDAO;
import com.example.lpp.examinationsystem.util.RestUtil;
import com.example.lpp.examinationsystem.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplateDetailActivity extends AppCompatActivity {
    private Spinner questionTypeSpinner;
    private ImageButton addTemplateItem;
    private ImageButton delBtn;
    private LinearLayout paperTemplate;
    private EditText editPaperDes;
    private Button addTemplate;
    private ImageButton menuBtn;
    private ListView listView;

    private List<String> questionTypes=new ArrayList<>();
    private static final int UPDATE_LABEL=1;
    private static final int INIT_TEMPLATE=2;
    private String templateDescript;
    private List<Label> labelList;
    private int addInvalidLabel=1;//0:不添加请选择；1：添加请选择
    private List<String> menuList=new ArrayList<>();
    private Map<Label,Integer> templateItems;

    private String recruitId;
    private String type;
    private String des;
    private String recruitName;
    private String templateId;
    private String templateName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_detail);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        //初始化标题栏
        TextView txt_top=(TextView) findViewById(R.id.txt_top);
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

        editPaperDes=(EditText) findViewById(R.id.paper_descrip);
        addTemplateItem=(ImageButton) findViewById(R.id.add_template_item);
        addTemplate=(Button) findViewById(R.id.add_template);
        paperTemplate=findViewById(R.id.template_items);
        final Intent intent=getIntent();
        recruitId=intent.getStringExtra("recruitId");
        type=intent.getStringExtra("project type");
        recruitName=intent.getStringExtra("project name");
        des=intent.getStringExtra("project description");
        templateId=intent.getStringExtra("template id");
        templateName=intent.getStringExtra("template name");
        txt_top.setText("模板："+templateId);
        editPaperDes.setText(templateName);
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
                //获取template items
                TemplateDAO templateDAO=new TemplateDAO();
                templateItems=StringUtil.parseTemplateItems(templateDAO.getObject(templateId).getItems());
                if (templateItems!=null){
                    Message message1=new Message();
                    message1.what=INIT_TEMPLATE;
                    handler.sendMessage(message1);
                }
            }
        }).start();
        addTemplateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTemplateItem();
            }
        });
    }
    private void initTemplateItems(){
        for (Map.Entry<Label,Integer> entry: templateItems.entrySet()){
            for (int i=0;i<labelList.size();i++){
                if (labelList.get(i).getId()==entry.getKey().getId()){
                    if (addInvalidLabel==1){
                        questionTypes.add("请选择");
                        addInvalidLabel=0;
                    }
                    View newItemView=View.inflate(this,R.layout.item_add_template,null);
                    questionTypeSpinner=(Spinner) newItemView.findViewById(R.id.question_type);
                    EditText itemCount=(EditText) newItemView.findViewById(R.id.question_count);
                    TemplateDetailActivity.Myadapter adapter2=new TemplateDetailActivity.Myadapter(this,android.R.layout.simple_spinner_dropdown_item,questionTypes);
                    questionTypeSpinner.setAdapter(adapter2);
                    questionTypeSpinner.setSelection(i,true);
                    questionTypeSpinner.setOnItemSelectedListener(new TemplateDetailActivity.questionTypeSpinnerListener());
                    itemCount.setText(entry.getValue().toString());
                    delBtn=(ImageButton) newItemView.findViewById(R.id.del_btn);
                    delBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            delTemplateItem(view);
                        }
                    });
                    paperTemplate.addView(newItemView);
                }
            }
        }
    }
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
        TemplateDetailActivity.Myadapter adapter2=new TemplateDetailActivity.Myadapter(this,android.R.layout.simple_spinner_dropdown_item,questionTypes);
        questionTypeSpinner.setAdapter(adapter2);
        questionTypeSpinner.setSelection(questionTypes.size()-1,true);
        questionTypeSpinner.setOnItemSelectedListener(new TemplateDetailActivity.questionTypeSpinnerListener());
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
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_LABEL:
                    break;
                case INIT_TEMPLATE:
                    initTemplateItems();
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
                        listView.setVisibility(View.GONE);
                        break;
                    case 1://删除项目
                        Toast.makeText(TemplateDetailActivity.this,"暂无删除项目功能",Toast.LENGTH_SHORT).show();
                        listView.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
