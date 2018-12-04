package com.example.lpp.examinationsystem;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ZhanghaoFragment extends Fragment{
    private TextView mTextView;

    public  ZhanghaoFragment(){}

    public static  ZhanghaoFragment newInstance(String text){
        ZhanghaoFragment fragmentOne = new ZhanghaoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", text);
        fragmentOne.setArguments(bundle);
        return fragmentOne;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xuqiu_fragment,container,false);
//        mTextView = (TextView)view.findViewById(R.id.txt_content);
//        if(getArguments()!=null){
//            mTextView.setText(getArguments().getString("name"));
//        }

        return view;
    }
}
