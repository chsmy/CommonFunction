package com.chs.commonfunction.coordinatorlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chs.commonfunction.R;
import com.chs.commonfunction.coordinatorlayout.recycleview.BaseRecycleAdapter;
import com.chs.commonfunction.coordinatorlayout.recycleview.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class SecondFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();
    private BaseRecycleAdapter mAdapter;
    public static SecondFragment newInstance(String title) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        initData();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BaseRecycleAdapter<String>(getContext(),R.layout.item,mList,mRecyclerView) {
            @Override
            public void convert(ViewHolder holder, String str) {
                holder.setText(R.id.tv_item,str);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private void initData() {
        for(int i = 0;i<20;i++){
            mList.add("item"+i);
        }
    }

}
