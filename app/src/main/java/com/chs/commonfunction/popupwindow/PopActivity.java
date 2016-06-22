package com.chs.commonfunction.popupwindow;

import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.chs.commonfunction.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：chs on 2016/6/22 15:06
 * 邮箱：657083984@qq.com
 */
public class PopActivity extends AppCompatActivity {
    @Bind(R.id.pop_up)
    Button pop_up;
    private int mScreenWidth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        ButterKnife.bind(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
    }

    @OnClick(R.id.pop_up)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_up:
                showPop();
                break;
        }
    }
    private void showPop(){
        View view = LayoutInflater.from(this).inflate(R.layout.pop_select,null);
        PopupWindow popupWindow = new PopupWindow(view, 3*mScreenWidth/4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(pop_up);
    }

}
