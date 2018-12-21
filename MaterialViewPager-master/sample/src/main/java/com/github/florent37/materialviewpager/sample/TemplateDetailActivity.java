package com.github.florent37.materialviewpager.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.github.florent37.materialviewpager.sample.model.Label;
import com.github.florent37.materialviewpager.sample.model.Paper;
import com.github.florent37.materialviewpager.sample.model.Template;
import com.github.florent37.materialviewpager.sample.rest.LabelDAO;
import com.github.florent37.materialviewpager.sample.rest.PaperDAO;
import com.github.florent37.materialviewpager.sample.rest.TemplateDAO;
import com.github.florent37.materialviewpager.sample.util.RestUtil;
import com.github.florent37.materialviewpager.sample.util.StringUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TemplateDetailActivity extends AppCompatActivity {
    private Spinner questionTypeSpinner;
    private at.markushi.ui.CircleButton addTemplateItem;
    private at.markushi.ui.CircleButton delBtn;
    private LinearLayout paperTemplate;
    private EditText editPaperDes;
    private com.beardedhen.androidbootstrap.BootstrapButton createPaper;
    private ImageButton menuBtn;
    private ListView listView;

    private List<String> questionTypes=new ArrayList<>();
    private static final int UPDATE_LABEL=1;
    private static final int INIT_TEMPLATE=2;
    private static final int SAVE_TEMPLATE_SUCCESS=3;
    private static final int SAVE_TEMPLATE_FAIL=4;
    private static final int CREATE_PAPER_SUCCESS=5;
    private static final int CREATE_PAPER_FAIL=6;
    private static int versionCount=0;
    private String templateDescript;
    private List<Label> labelList;
    private int addInvalidLabel=1;//0:不添加请选择；1：添加请选择
    private List<String> menuList=new ArrayList<>();
    private Map<Label,Integer> templateItems;
    private Map<Label,Integer> newTemplateItems;

    private String recruitId;
    private String type;
    private String des;
    private String recruitName;
    private String templateId;
    private String templateName;

    private int paperIndexNum;
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

        //试卷数字
        String paperIndex=load();
        if (!TextUtils.isEmpty(paperIndex)){
            paperIndexNum=Integer.parseInt(paperIndex);
        }else{
            save("0");
            paperIndexNum=0;
        }

        editPaperDes=(EditText) findViewById(R.id.paper_descrip);
        addTemplateItem=(at.markushi.ui.CircleButton) findViewById(R.id.add_template_item);
        createPaper=(com.beardedhen.androidbootstrap.BootstrapButton) findViewById(R.id.create_paper);
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
        createPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //导出试卷
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PaperDAO paperDAO=new PaperDAO();
                        Paper newPaper=new Paper();
                        newPaper.setRecruitId(recruitId);
                        newPaper.setName(templateName+paperIndexNum+"");
                        TemplateDAO templateDAO=new TemplateDAO();
                        newPaper.setQuestions(RestUtil.getQuestionsByTemplate(templateDAO.getObject(templateId)));
                        if (paperDAO.postObject(newPaper)!=null){
                            Message message=new Message();
                            message.what=CREATE_PAPER_SUCCESS;
                            handler.sendMessage(message);
                            paperIndexNum++;
                            save(paperIndexNum+"");
                        }else{
                            Message message=new Message();
                            message.what=CREATE_PAPER_FAIL;
                            handler.sendMessage(message);
                        }

                    }
                }).start();
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
                    delBtn=(at.markushi.ui.CircleButton) newItemView.findViewById(R.id.del_btn);
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
        delBtn=(at.markushi.ui.CircleButton) newItemView.findViewById(R.id.del_btn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delTemplateItem(view);
            }
        });
        paperTemplate.addView(newItemView);
    }
    private void delTemplateItem(View view){
        paperTemplate.removeView((View) view.getParent().getParent());
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
    private void saveTemplateSuccess(){
        Toast.makeText(TemplateDetailActivity.this,"保存模板成功",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.putExtra("project type",type);
        intent.putExtra("project id",recruitId);
        intent.putExtra("project description",des);
        intent.putExtra("project name",recruitName);
        setResult(RESULT_OK,intent);
        finish();
    }
    private void saveTemplateFail(){
        Toast.makeText(TemplateDetailActivity.this,"保存模板失败，请稍后重试",Toast.LENGTH_SHORT).show();
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
                case SAVE_TEMPLATE_SUCCESS:
                    saveTemplateSuccess();
                    break;
                case SAVE_TEMPLATE_FAIL:
                    saveTemplateFail();
                    break;
                case CREATE_PAPER_SUCCESS:
                    createPaperSuccess();
                    break;
                case CREATE_PAPER_FAIL:
                    createPaperFail();
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
                        if (editPaperDes.getText().length()>0&&paperTemplate.getChildCount()>0){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    TemplateDAO templateDAO=new TemplateDAO();
                                    Template newTemplate=new Template();
                                    newTemplateItems=new HashMap<>();
                                    newTemplate.setRecruitId(recruitId);
                                    newTemplate.setId(Long.valueOf(templateId));
                                    newTemplate.setName(editPaperDes.getText().toString());
                                    for (int i=0;i<paperTemplate.getChildCount();i++){
                                        Spinner itemSpinner=(Spinner) paperTemplate.getChildAt(i).findViewById(R.id.question_type);
                                        EditText itemCount=(EditText) paperTemplate.getChildAt(i).findViewById(R.id.question_count);
                                        System.out.println(labelList.get(itemSpinner.getSelectedItemPosition())+" "+itemCount.getText().toString());
                                        newTemplateItems.put(labelList.get(itemSpinner.getSelectedItemPosition()),Integer.parseInt(itemCount.getText().toString()));
                                    }
                                    newTemplate.setItems(StringUtil.buildTemplateItems(newTemplateItems));
                                    Template res=templateDAO.putObject(newTemplate);
                                    if (res!=null){
                                        Message message=new Message();
                                        message.what=SAVE_TEMPLATE_SUCCESS;
                                        handler.sendMessage(message);
                                    }else {
                                        Message message=new Message();
                                        message.what=SAVE_TEMPLATE_FAIL;
                                        handler.sendMessage(message);
                                    }
                                }
                            }).start();
                        }else {
                            Toast.makeText(TemplateDetailActivity.this,"必须填写试卷描述，正确设置模板",Toast.LENGTH_SHORT).show();
                        }
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

    private void createPaperSuccess(){
        Toast.makeText(TemplateDetailActivity.this,"导出试卷成功",Toast.LENGTH_SHORT).show();
    }
    private void createPaperFail(){
        Toast.makeText(TemplateDetailActivity.this,"导出试卷失败，请稍后重试",Toast.LENGTH_SHORT).show();
    }

    public void save(String inputText){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try {
            out=openFileOutput("data_count",Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (writer!=null)
                    writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public String load(){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try{
            in=openFileInput("data_count");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line=reader.readLine())!=null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}