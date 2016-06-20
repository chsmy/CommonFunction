package com.chs.commonfunction.prutorefresh;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chs.commonfunction.R;
import com.chs.commonfunction.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 作者：chs on 2016/6/4 17:36
 * 邮箱：657083984@qq.com
 */
public class PruToRefreshActivity extends AppCompatActivity {
    @Bind(R.id.recycle_view)
    LoadMoreRecyclerView mRecyclerView;
    @Bind(R.id.material_style_ptr_frame)
    PtrClassicFrameLayout mPtrFrame;
    private List<String> mData = new ArrayList<>();
    private CommonAdapter mAdapter;
    private int currentPage = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            currentPage++;
            if (msg.what == 1) {
                mPtrFrame.refreshComplete();
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pru_to_refresh);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mAdapter.updateShowDialog(1);
                initData();
            }
        });
    }

    private void initView() {
        mAdapter = new CommonAdapter<String>(this, R.layout.item_recycleview, mData, mRecyclerView) {
            @Override
            public void convert(ViewHolder holder, String str) {
                holder.setText(R.id.tv_text, str);
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this));
        mRecyclerView.setAdapter(mAdapter);
        initPtr();
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 20; i++) {
                    mData.add("item>>>>>>>" + i * currentPage);
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    private void initPtr() {
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage = 1;
                mData.clear();
                initData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
    }
}
