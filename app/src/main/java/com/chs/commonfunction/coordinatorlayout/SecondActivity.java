package com.chs.commonfunction.coordinatorlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chs.commonfunction.R;
import com.chs.commonfunction.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager viewpager;
    private String[] chinnals = new String[]{"first","second","third"};
    private List<SecondFragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mMyPagerAdapter;
    int mCurrentPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().hide();
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        initFragment();

        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mMyPagerAdapter);

        mSlidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.green));
        mSlidingTabLayout.setDistributeEvenly(isDistributeEvenly());
        mSlidingTabLayout.setViewPager(viewpager);
        mSlidingTabLayout.setOnPageChangeListener(this);
        mSlidingTabLayout.setCurrent(mCurrentPosition);
    }

    private void initFragment() {
        for(String str:chinnals){
            mFragments.add(SecondFragment.newInstance(str));
        }
    }
    protected boolean isDistributeEvenly() {
        return chinnals.length <= 5;
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

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return chinnals.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = chinnals[position];
            return title;
        }
    }
}
