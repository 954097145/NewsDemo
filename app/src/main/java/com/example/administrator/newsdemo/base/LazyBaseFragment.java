package com.example.administrator.newsdemo.base;

import android.util.Log;

/**
 * Created by Administrator on 2016/11/1.
 */

public abstract class LazyBaseFragment extends BaseFragment {
    protected boolean isVisible, isPrepared, hasLoaded;//是否可见，视图是否已经创建完毕，数据是否已经加载完毕，是否需要更新数据

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: " + isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible&&isPrepared&&!hasLoaded){
            hasLoaded=lazyLoad();
        }
    }


    protected abstract boolean lazyLoad();


    protected void updateData() {

    }

    @Override
    protected void initData() {
        isPrepared = true;
        if (!isVisible || !isPrepared || hasLoaded) {
            return;//不可见状态，和视图未准备好，或者已经加载过，都不进行数据加载
        }
        hasLoaded = lazyLoad();

    }
}
