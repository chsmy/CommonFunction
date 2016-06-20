package com.chs.commonfunction.tabselector.top;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作者：chs on 2016/6/17 16:03
 * 邮箱：657083984@qq.com
 */
public class TabFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("TabFragment");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
