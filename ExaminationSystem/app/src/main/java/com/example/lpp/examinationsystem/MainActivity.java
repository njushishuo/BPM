package com.example.lpp.examinationsystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView topBar;
    private TextView tabXuqiu;
    private TextView tabShijuan;
    private TextView tabZhanghao;

    private FrameLayout ly_content;

    private XuqiuFragment f1;
    private ShijuanFragment f2;
    private ZhanghaoFragment f3;
    private FragmentManager fragmentManager;
    private ImageButton addRequirement;
    private ImageButton deleteRequirement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }

        bindView();
    }

    //UI组件初始化与事件绑定
    private void bindView() {
        topBar = (TextView)this.findViewById(R.id.txt_top);
        tabXuqiu = (TextView)this.findViewById(R.id.txt_xuqiu);
        tabShijuan = (TextView)this.findViewById(R.id.txt_shijuan);
        tabZhanghao = (TextView)this.findViewById(R.id.txt_zhanghao);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);
        addRequirement=(ImageButton) findViewById(R.id.add_rqm);
        deleteRequirement=(ImageButton) findViewById(R.id.delete_rqm);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        selected();
        tabXuqiu.setSelected(true);
        if(f1==null){
            f1 = new XuqiuFragment();
            transaction.add(R.id.fragment_container,f1);
        }else{
            transaction.show(f1);
        }
        transaction.commit();
        //底栏需求控件事件
        tabXuqiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                hideAllFragment(transaction);
                selected();
                tabXuqiu.setSelected(true);
                topBar.setText("需求");
                if(f1==null){
                    f1 = new XuqiuFragment();
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                if (addRequirement.getVisibility()==View.GONE){
                    addRequirement.setVisibility(View.VISIBLE);
                }
                transaction.commit();
            }
        });
        //底栏试卷控件事件
        tabShijuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                hideAllFragment(transaction);

                selected();
                tabShijuan.setSelected(true);
                topBar.setText("试卷");
                    f2 = new ShijuanFragment();
                    f2=f2.newInstance("试卷");
                    transaction.add(R.id.fragment_container,f2);
                if (addRequirement.getVisibility()==View.VISIBLE){
                    addRequirement.setVisibility(View.GONE);
                }
                transaction.commit();
            }
        });
        //底栏账号控件事件
        tabZhanghao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                hideAllFragment(transaction);
                selected();
                tabZhanghao.setSelected(true);
                topBar.setText("账号");
                if(f3==null){
                    f3 = new ZhanghaoFragment();
                    f3=f3.newInstance("账号");
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);
                }
                if (addRequirement.getVisibility()==View.VISIBLE){
                    addRequirement.setVisibility(View.GONE);
                }
                transaction.commit();
            }
        });
        //添加需求
        addRequirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddXuqiuActivity.class);
                startActivity(intent);
            }
        });
        //删除需求
    }

    //重置所有文本的选中状态
    public void selected(){
        tabXuqiu.setSelected(false);
        tabShijuan.setSelected(false);
        tabZhanghao.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
    }
}
