package com.example.administrator.newsdemo.fragment;



import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.administrator.newsdemo.R;
import com.example.administrator.newsdemo.activities.MainActivity;
import com.example.administrator.newsdemo.adapter.NewsTypesPagerAdapter;
import com.example.administrator.newsdemo.base.BaseFragment;
import com.example.administrator.newsdemo.entity.NewsType;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/28.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.indicator)
    TabPageIndicator mIndicator;
    @BindView(R.id.pager)
    ViewPager mPager;
    private NewsTypesPagerAdapter adapter;

    @Override
    protected void initData() {
        Bundle bundle=getArguments();
        NewsType type= (NewsType) bundle.getSerializable(MainActivity.KEY_TYPELIST);
        adapter = new NewsTypesPagerAdapter(getFragmentManager(),type);
        mPager.setAdapter(adapter);
        mIndicator.setViewPager(mPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_home;
    }

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment hf = new HomeFragment();
        hf.setArguments(bundle);
        return hf;
    }
}
