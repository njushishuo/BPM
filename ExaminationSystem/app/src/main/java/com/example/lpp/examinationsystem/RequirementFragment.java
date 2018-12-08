package com.example.lpp.examinationsystem;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RequirementFragment extends ListFragment {
    private List<Requirement> xuqiuList;

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
        RequirementAdapter adapter = new RequirementAdapter(getContext(),R.layout.xuqiu_item,xuqiuList);
        this.setListAdapter(adapter);
    }

    private void initXuqiu(){
        Requirement x1=new Requirement("1234","2018-11-22","进行中");
        xuqiuList.add(x1);
        Requirement x2=new Requirement("2333","2018-01-11","已结束");
        xuqiuList.add(x2);
        Requirement x3=new Requirement("3344","2018-05-11","已结束");
        xuqiuList.add(x3);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Requirement item=xuqiuList.get(position);
        String project_id=item.getId();
        Log.d("RequirementFragment",project_id);
        Intent intent=new Intent(getActivity(),RequirementDetailActivity.class);
        intent.putExtra("project id",project_id);
        startActivity(intent);
    }


}
