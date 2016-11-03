package com.example.administrator.newsdemo.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.administrator.newsdemo.R;
import com.example.administrator.newsdemo.adapter.FavorAdapter;
import com.example.administrator.newsdemo.base.BaseFragment;
import com.example.administrator.newsdemo.db.DBManager;
import com.example.administrator.newsdemo.view.RecycleViewDivider;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/28.
 */

public class FavorFragment extends BaseFragment implements FavorAdapter.OnItemClickListener
{
    @BindView(R.id.recyclerView1)
    RecyclerView mRecyclerView1;
    FavorAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean dataLoaded;

    @Override
    protected void initData() {

        adapter = new FavorAdapter(DBManager.getDBManager(getContext()).queryAllList());
        adapter.setOnItemClickListener(FavorFragment.this);
        mRecyclerView1.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView1.setLayoutManager(layoutManager);
        mRecyclerView1.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        dataLoaded = true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_favor;
    }

    @Override
    public void onClick(int position) {
        //点击进行对话框选项删除
        showDeleteDialog(position);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && dataLoaded) {
            reloadDataAndRefreshUI();
        }
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setMessage("删除这条收藏的新闻吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String docId = adapter.getDataList().get(position).docid;
                int deleteNum = DBManager.getDBManager(FavorFragment.this.getContext()).removeNewsById(docId);
                if (deleteNum > 0) {
                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                }
                reloadDataAndRefreshUI();
            }
        }).setNegativeButton("取消", null);
        b.show();
    }

    private void reloadDataAndRefreshUI() {
        adapter.setDataList(DBManager.getDBManager(getContext()).queryAllList());
        adapter.notifyDataSetChanged();
    }
}
