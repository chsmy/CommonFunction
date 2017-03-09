package com.chs.commonfunction.coordinatorlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.chs.commonfunction.R;
import com.chs.commonfunction.coordinatorlayout.recycleview.BaseRecycleAdapter;
import com.chs.commonfunction.coordinatorlayout.recycleview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {
    private RecyclerView recycleview;
    private List<String> mList = new ArrayList<>();
    private BaseRecycleAdapter mAdapter;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initData();
        getSupportActionBar().hide();
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("CoordinatorLayout");
        LinearLayoutManager layoutManager =  new LinearLayoutManager(this);
        recycleview.setLayoutManager(layoutManager);
        mAdapter = new BaseRecycleAdapter<String>(this,R.layout.item,mList,recycleview) {
            @Override
            public void convert(ViewHolder holder, String str) {
                holder.setText(R.id.tv_item,str);
            }
        };
        recycleview.setAdapter(mAdapter);
    }
    private void initData() {
        for(int i = 0;i<20;i++){
            mList.add("item"+i);
        }
    }
}
