package com.github.florent37.materialviewpager.sample.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.model.Paper;
import com.github.florent37.materialviewpager.sample.model.PaperVO;
import com.github.florent37.materialviewpager.sample.rest.DAOFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaperFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;

    private static final int UPDATE_LIST = 1;

    protected PaperViewAdapter adapter = new PaperViewAdapter();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static PaperFragment newInstance() {
        return new PaperFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_LIST:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Paper> papers = DAOFactory.getPaperDAO().getList();
                List<PaperVO> contents = new ArrayList<>();
                for (Paper paper : papers) {
                    contents.add(new PaperVO(paper));
                }
                adapter.papers = contents;
                Message message = new Message();
                message.what = UPDATE_LIST;
                handler.sendMessage(message);
            }
        }).start();

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(adapter);
    }
}
