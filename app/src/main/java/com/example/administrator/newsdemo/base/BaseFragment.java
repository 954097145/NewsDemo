package com.example.administrator.newsdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/28.
 */

public abstract class BaseFragment extends Fragment {
    private View rootView;

    public BaseFragment(){}

    protected static String TAG="dyx_BaseFragment";
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
                             Bundle saveInstanceState){
        Log.d(TAG, "onCreateView: ");
        if (rootView==null){
            rootView=inflater.inflate(getLayoutId(),null);
            ButterKnife.bind(this,rootView);
            initData();
        }


        return rootView;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        initData();
//    }

    protected abstract void initData();
    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
        if (rootView!=null){
            ViewGroup parent= (ViewGroup) rootView.getParent();
            if (parent!=null){
                parent.removeView(rootView);
                //Toast.makeText(getContext(), parent+""+rootView, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG=""+getClass().getSimpleName();
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }
}
