package com.example.lpp.examinationsystem;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class XuqiuFragment extends ListFragment {
    private List<Xuqiu> xuqiuList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xuqiu_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xuqiuList = new ArrayList<>();
        initXuqiu();
        XuqiuAdapter adapter = new XuqiuAdapter(getContext(),R.layout.xuqiu_item,xuqiuList);
        this.setListAdapter(adapter);
    }

    private void initXuqiu(){
        Xuqiu x1=new Xuqiu("1234","2018-11-22","进行中");
        xuqiuList.add(x1);
        Xuqiu x2=new Xuqiu("2333","2018-01-11","已结束");
        xuqiuList.add(x2);
        Xuqiu x3=new Xuqiu("3344","2018-05-11","已结束");
        xuqiuList.add(x3);
    }
}
