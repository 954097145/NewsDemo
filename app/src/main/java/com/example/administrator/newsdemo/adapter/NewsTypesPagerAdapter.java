package com.example.administrator.newsdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.newsdemo.entity.NewsType;
import com.example.administrator.newsdemo.fragment.NewsListFragment;

/**
 * Created by Administrator on 2016/10/28.
 */

public class NewsTypesPagerAdapter extends FragmentPagerAdapter {
    NewsType newsType;

    public NewsTypesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public NewsTypesPagerAdapter(FragmentManager fragmentManager, NewsType type) {
        super(fragmentManager);
        this.newsType = type;
    }

    @Override
    public Fragment getItem(int position) {
        //持有着viewpager要显示的视图的那个碎片对象
        return NewsListFragment.newInstance(newsType.tList.get(position).tid, newsType.tList.get(position).tname);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return newsType.tList.get(position).tname;
    }

    @Override
    public int getCount() {

        return newsType.tList == null ? 0 : newsType.tList.size();
    }
}
