package com.example.lpp.examinationsystem;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lpp.examinationsystem.model.Paper;
import com.example.lpp.examinationsystem.model.PaperList;
import com.example.lpp.examinationsystem.model.Question;
import com.example.lpp.examinationsystem.rest.PaperDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaperFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shijuan_fragment, container, false);
    }

    private List<Paper> paperList;
    private final static int UPDATE_LIST=0;
    PaperAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paperList = new ArrayList<>();
        adapter = new PaperAdapter(getContext(),R.layout.paper_item,paperList);
        this.setListAdapter(adapter);
        initPaper();
    }

    private void initPaper(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                PaperDAO PaperDao=new PaperDAO();
                List list= PaperDao.getList();
                for (int i=0;i<list.size();i++){
                    Paper p=new Paper();
                    p=(Paper) list.get(i);
                    paperList.add(p);
                }
                Message message=new Message();
                message.what=UPDATE_LIST;
                handler.sendMessage(message);
            }
        }).start();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_LIST:
//                    System.out.println(paperList);
                    adapter.notifyDataSetChanged();
                    break;
                default:break;
            }
        }
    };
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Paper item=paperList.get(position);
        long paper_id=item.getId();
//        Log.d("PaperFragment",String.valueOf(paper_id));
        Intent intent=new Intent(getActivity(),PaperDetailActivity.class);
        intent.putExtra("paper id",String.valueOf(paper_id));
        startActivity(intent);
    }


}
