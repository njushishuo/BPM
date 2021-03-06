package com.github.florent37.materialviewpager.sample.fragment;

import android.content.Intent;
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
import com.github.florent37.materialviewpager.sample.RecruitDetailActivity;
import com.github.florent37.materialviewpager.sample.model.Recruit;
import com.github.florent37.materialviewpager.sample.rest.DAOFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecruitFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;

    private static final int UPDATE_LIST = 1;

    protected RecruitViewAdapter adapter = new RecruitViewAdapter();

    private List<Recruit> items;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static RecruitFragment newInstance() {
        return new RecruitFragment();
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
                items = DAOFactory.getRecruitDAO().getList();
                adapter.contents = items;
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

        adapter.setListener(new OnClickRecruitItemRecyclerListener() {
            @Override
            public void onRecruitItemClick(View view, int position) {
                Recruit item=items.get(position);
                String project_name=item.getName();
                Intent intent=new Intent(getActivity(),RecruitDetailActivity.class);
                intent.putExtra("project id",item.getId()+"");
                intent.putExtra("project name",project_name);
                intent.putExtra("project type",item.getType());
                intent.putExtra("project description",item.getDescription());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                items = DAOFactory.getRecruitDAO().getList();
                adapter.contents = items;
                Message message = new Message();
                message.what = UPDATE_LIST;
                handler.sendMessage(message);
            }
        }).start();
    }
}
