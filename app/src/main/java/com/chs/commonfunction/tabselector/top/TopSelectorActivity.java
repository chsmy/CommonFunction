package com.chs.commonfunction.tabselector.top;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chs.commonfunction.R;
import com.chs.commonfunction.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：chs on 2016/6/17 14:50
 * 邮箱：657083984@qq.com
 */
public class TopSelectorActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    private List<String> mTabData;
    private List<Fragment> listFragments = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tab_selector);
        ButterKnife.bind(this);
        initData();
        initViewPagerAndTab();
    }

    private void initData() {
        mTabData = new ArrayList<>();
        mTabData.add("新闻");
        mTabData.add("生活");
        mTabData.add("健康");
        mTabData.add("娱乐");
        mTabData.add("体育");
        mTabData.add("社会");
        for(String str: mTabData){
            listFragments.add(new TabFragment());
        }
    }

    private void initViewPagerAndTab() {
        GroupPagerAdapter adapter = new GroupPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabs);
        int mCurrentPosition = 0;
        viewPager.setCurrentItem(mCurrentPosition);
        mSlidingTabLayout.setCustomTabView(R.layout.comm_lay_tab_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.comm_tab_selected_strip));
        mSlidingTabLayout.setDistributeEvenly(isDistributeEvenly());
        mSlidingTabLayout.setViewPager(viewPager);
        mSlidingTabLayout.setOnPageChangeListener(this);
        mSlidingTabLayout.setCurrent(mCurrentPosition);
    }
    protected boolean isDistributeEvenly() {
        return mTabData.size() <= 5;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private class GroupPagerAdapter extends FragmentStatePagerAdapter {
        public GroupPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return listFragments.get(i);
        }

        @Override
        public int getCount() {
            return mTabData.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = mTabData.get(position);
            return title;
        }
    }
}
