package com.example.administrator.newsdemo.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.newsdemo.R;
import com.example.administrator.newsdemo.biz.Xhttp;
import com.example.administrator.newsdemo.common.IgnoreTypes;
import com.example.administrator.newsdemo.entity.NewsType;

import java.util.ArrayList;
import java.util.List;

public class LogoActivity extends AppCompatActivity implements Xhttp.NewsTypeListener {
    private NewsType newsType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        Xhttp.getNewsTypeList(this);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, LogoActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onSuccess(NewsType newsType) {
        //-MainActivity----HomeFragment----NewTyepAdapter--设置标题

        if (newsType != null) {
            this.newsType = newsType;
            //排除那些没有数据结果的分类
            ignore(newsType);
        }

    }

    @Override
    public void onFinish() {
        Bundle bundle = new Bundle();
        MainActivity.start(this, newsType);
        finish();
    }

    private void ignore(NewsType newsType) {
        List<NewsType.SubName> tobeDeleted = new ArrayList<>();
        for (int i = 0; i < IgnoreTypes.TYPES.length; i++) {
            for (int j = 0; j <newsType.tList.size(); j++) {
                if (IgnoreTypes.TYPES[i].equals(newsType.tList.get(j).tname)) {
                    tobeDeleted.add(newsType.tList.get(j));
                }
            }
        }
        newsType.tList.removeAll(tobeDeleted);
    }
}
