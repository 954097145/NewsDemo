package com.example.administrator.newsdemo.adapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsdemo.R;
import com.example.administrator.newsdemo.entity.NetEase;
import com.example.administrator.newsdemo.utils.XImageUtil;

import java.util.List;


/**
 * Created by Administrator on 2016/10/29.
 */

public class ADVpager extends PagerAdapter{
    private List<NetEase.Ad> list;

    public ADVpager(List<NetEase.Ad> list) {
        this.list = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(container.getContext(), R.layout.layout_item_one_head, null);
        TextView mTitile = (TextView) view.findViewById(R.id.tv_title);
        ImageView imgView = (ImageView) view.findViewById(R.id.img_head);
        mTitile.setText(list.get(position % list.size()).title);
        XImageUtil.display(imgView, list.get(position % list.size()).imgsrc);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
