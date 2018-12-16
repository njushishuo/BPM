package com.example.lpp.examinationsystem;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpp.examinationsystem.model.Recruit;
import com.example.lpp.examinationsystem.model.RecruitList;
import com.example.lpp.examinationsystem.model.Template;
import com.example.lpp.examinationsystem.rest.BaseDAO;
import com.example.lpp.examinationsystem.rest.RecruitDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequirementFragment extends ListFragment implements Serializable {
    private List<Recruit> recruitList;
    private static final int UPDATE_LIST=1;
    RequirementAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xuqiu_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recruitList = new ArrayList<>();
        adapter= new RequirementAdapter(getContext(),R.layout.xuqiu_item,recruitList);
        RequirementFragment.this.setListAdapter(adapter);
        initXuqiu();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_LIST:
                    adapter.notifyDataSetChanged();
                    break;
                    default:break;
            }
        }
    };
    private void initXuqiu(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RecruitDAO recruitDAO=new RecruitDAO();
                List list=recruitDAO.getList();
                int l=list.size();
                for (int i=0;i<l;i++){
                    Recruit r=new Recruit();
                    r=(Recruit) list.get(i);
                    recruitList.add(r);
                }
//                System.out.println(recruitList);
                Message message=new Message();
                message.what=UPDATE_LIST;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Recruit item=recruitList.get(position);
        String project_name=item.getName();
        Intent intent=new Intent(getActivity(),RequirementDetailActivity.class);
        intent.putExtra("project id",item.getId()+"");
        intent.putExtra("project name",project_name);
        intent.putExtra("project type",item.getType());
        intent.putExtra("project description",item.getDescription());
        startActivity(intent);
    }
}
