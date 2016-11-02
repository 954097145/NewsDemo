package com.example.administrator.newsdemo.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.example.administrator.newsdemo.R;
import com.example.administrator.newsdemo.adapter.NetEaseAdapter;
import com.example.administrator.newsdemo.base.BaseFragment;
import com.example.administrator.newsdemo.base.LazyBaseFragment;
import com.example.administrator.newsdemo.biz.Xhttp;
import com.example.administrator.newsdemo.common.CommonUrls;
import com.example.administrator.newsdemo.entity.NetEase;
import com.example.administrator.newsdemo.view.RecycleViewDivider;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import java.util.List;
import java.util.TimerTask;

import butterknife.BindView;

public class NewsListFragment extends LazyBaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        NetEaseAdapter.OnItemClickListener{
    private static final String KEY_TID = "key_tid";
    private static final String KEY_TNAME = "key_tname";
    @BindView(R.id.recyclerView1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.swipe1)
    SwipeRefreshLayout mSwipe1;
    private String tid, tname;
    private OnFragmentInteractionListener mListener;
    private RecyclerView.OnScrollListener lis = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!mSwipe1.isRefreshing()) {
                int lastItemPosition = layoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition == mNetEaseAdapter.getItemCount() - 1) {
                    //加载数据
                    mNetEaseAdapter.setCurrentState(NetEaseAdapter.FOOTER_PULLING);

                    //获取新数据，url
                    Xhttp.getNewsList(CommonUrls.getUrl(tid), tid, new Xhttp.OnSuccessListener() {
                        @Override
                        public void setNewsList(List<NetEase> neteaseNews) {
                            mNetEaseAdapter.addDataList(neteaseNews);
                            mNetEaseAdapter.notifyDataSetChanged();
                            if (neteaseNews.size() == 0) {
                                mNetEaseAdapter.setCurrentState(NetEaseAdapter.FOOTER_PULL_NO_DATA);
                            } else {
                                mNetEaseAdapter.setCurrentState(NetEaseAdapter.FOOTER_PULL_FINISHED);
                            }
                        }
                    });
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
    private NetEaseAdapter mNetEaseAdapter;
    private LinearLayoutManager layoutManager;
    private Xhttp.OnSuccessListener listener = new Xhttp.OnSuccessListener() {
        @Override
        public void setNewsList(List<NetEase> neteaseNews) {
            //            pd.dismiss();
            mNetEaseAdapter = new NetEaseAdapter(neteaseNews);
            mNetEaseAdapter.setOnItemClickListener(NewsListFragment.this);
            mRecyclerView1.setAdapter(mNetEaseAdapter);
            //必须要设置一个布局管理器 //listview,gridview,瀑布流
            layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView1.setLayoutManager(layoutManager);
            //   mRecyclerView1.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
            // mRecyclerView1.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.HORIZONTAL, false));
            //   mRecyclerView1.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            //分割线
            mRecyclerView1.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));

        }
    };
    private Handler mHandler;
    private ProgressDialog pd;

    public NewsListFragment() {

    }

    //实现懒加载后的加载数据方法
    @Override
    protected boolean lazyLoad() {
        //        //加一个进度条对话框
        //      pd=new ProgressDialog(getContext());
        //        pd.setMessage("正在加载，请稍候...");
        //        pd.setCancelable(false);
        //        pd.show();
//        Log.d(TAG, "lazyLoad: 加载数据");
        Log.d(TAG, "lazyLoad: 加载数据");
        //        mTvText.setText(tid + "------" + tname);
        mSwipe1.setOnRefreshListener(this);
        mRecyclerView1.addOnScrollListener(lis);
        Xhttp.getNewsList(CommonUrls.getUrl(tid), tid, listener);
        // Xhttp.getNewsList(url, "T1370583240249", listener);
        return true;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    //


    public static NewsListFragment newInstance(String tid, String tname) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TID, tid);
        args.putString(KEY_TNAME, tname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tid = getArguments().getString(KEY_TID);
            tname = getArguments().getString(KEY_TNAME);
        }
        mHandler = new Handler();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        Runnable runable = new TimerTask() {
            @Override
            public void run() {
                NetEase netEase = mNetEaseAdapter.getDataList().get(1);
                mNetEaseAdapter.addData(1, netEase);
                mNetEaseAdapter.notifyItemInserted(1);
                Toast.makeText(getContext(), "加载数据完毕", Toast.LENGTH_SHORT).show();
                mSwipe1.setRefreshing(false);

            }
        };
        mHandler.postDelayed(runable, 2000);
    }

    @Override
    public void onClick(int position) {
        //list点击事件：传递docid到浏览页面
        //当前fragment--->所在的Activity--->下一个Activity-->新闻内容碎片

        onButtonPressed(mNetEaseAdapter.getDataList().get(position).docid);
    }

    public void onButtonPressed(String docid) {
        if (mListener != null) {
            mListener.onFragmentInteraction(docid);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String docId);
    }
}
