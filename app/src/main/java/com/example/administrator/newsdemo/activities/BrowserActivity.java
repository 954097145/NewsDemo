package com.example.administrator.newsdemo.activities;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.newsdemo.R;
import com.example.administrator.newsdemo.fragment.NewsContentFragment;

/**
 * Created by Administrator on 2016/11/1.
 */

public class BrowserActivity extends AppCompatActivity{
    public static final String KEY_DOCID = "docId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        //将docId交给显示新闻内容的碎片
        replaceFragment(NewsContentFragment.newInstance(getIntent().getStringExtra(KEY_DOCID)),false);
    }
    //是否添加到返回栈
    public void replaceFragment(Fragment f,boolean isAddtoBackStack) {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction().replace(R.id.activity_browser, f, f.getClass().getSimpleName());
        if (isAddtoBackStack){
            tr.addToBackStack(null);
        }
        tr.commit();
    }

    public static void start(Context context, String docId) {
        Intent starter = new Intent(context, BrowserActivity.class);
        starter.putExtra(KEY_DOCID, docId);
        context.startActivity(starter);
    }
}
