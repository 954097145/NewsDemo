package com.example.administrator.newsdemo.adapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.newsdemo.R;
import com.example.administrator.newsdemo.entity.NetEase;
import com.example.administrator.newsdemo.utils.XImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Administrator on 2016/11/2.
 */

public class FavorAdapter extends RecyclerView.Adapter<FavorAdapter.OneImageHolder>{
    public FavorAdapter(List<NetEase> dataList) {

        this.dataList = dataList;
    }

    public List<NetEase> getDataList() {

        return dataList;
    }


    public void setDataList(List<NetEase> dataList) {

        this.dataList = dataList;
    }

    public void addDataList(List<NetEase> list) {
        if (list == null) {
            Log.d("addDataList", "addDataList: 集合不能为空");
            return;
        }
        dataList.addAll(list);
    }

    public void addData(NetEase netEase) {

        dataList.add(netEase);
    }

    public void addData(int position, NetEase netEase) {

        dataList.add(position, netEase);
    }

    private List<NetEase> dataList;

    @Override
    public OneImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.layout_item_one_img, null);
        OneImageHolder holder = new OneImageHolder(itemView);
        return holder;
    }

    private OnItemClickListener onItemClickListener;

    @Override
    public void onBindViewHolder(OneImageHolder holder, final int position) {
        initOneImageView(holder, dataList.get(position));
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(position);
                }
            });
        }
    }

    /**
     * 左边大图：
     *
     * @param h
     * @param netEase
     */
    private void initOneImageView(OneImageHolder h, NetEase netEase) {
        XImageUtil.display(h.mImgLeft, netEase.imgsrc);
        h.mTvTitle.setText(netEase.title);
        h.mTvFollow.setText(netEase.replyCount + "");
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    //点击事件：
    public interface OnItemClickListener {
        void onClick(int position);
    }


    /**
     * 1.左边是大图，右边显示标题，和跟帖数
     */

    public static class OneImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_left)
        ImageView mImgLeft;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_follow)
        TextView mTvFollow;

        public OneImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {

        return dataList == null ? 0 : dataList.size();
    }
}
