package com.example.administrator.newsdemo.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.administrator.newsdemo.R;
import com.example.administrator.newsdemo.entity.NetEase;
import com.example.administrator.newsdemo.entity.NewsType;
import com.example.administrator.newsdemo.fragment.FavorFragment;
import com.example.administrator.newsdemo.fragment.HomeFragment;
import com.example.administrator.newsdemo.fragment.HotFragment;
import com.example.administrator.newsdemo.fragment.NewsListFragment;
import com.example.administrator.newsdemo.fragment.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener,
        NewsListFragment.OnFragmentInteractionListener {
    public static final String KEY_TYPELIST = "keyList";
    HomeFragment mHomeFragment;
    HotFragment mHotFragment;
    FavorFragment mFavorFragment;
    SettingFragment mSettingFragment;
    @BindView(R.id.radioGroup1)
    RadioGroup mRadioGroup1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //默认显示是home：
        mHomeFragment = HomeFragment.newInstance(getIntent().getExtras());
        mHotFragment = new HotFragment();
        mSettingFragment = new SettingFragment();
        mFavorFragment = new FavorFragment();
        showFragment(mHomeFragment);
        mRadioGroup1.setOnCheckedChangeListener(this);
    }

    /**
     * 默认替换方式，会将旧的fragment销毁
     * @param container
     * @param f
     */
    public void replaceFragment(int container, Fragment f) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        tr.replace(container, f, f.getClass().getSimpleName());
        tr.commit();

    }

    /**
     * 新的替换方法：只将fragment隐藏了
     * @param f
     */
    public void showFragment(Fragment f) {


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        String simpleName = f.getClass().getSimpleName();
        //判断要显示的fragment是否已经添加过，如果添加过，直接显示，没有添加过的，先添加，再显示。
        if (fm.findFragmentByTag(simpleName) == null) {
            tr.add(R.id.main_content, f, f.getClass().getSimpleName());
        }
        tr.hide(mFavorFragment);
        tr.hide(mHomeFragment);
        tr.hide(mHotFragment);
        tr.hide(mSettingFragment);
        tr.show(f);
        //   //切换到1个fragment时，隐藏其他的
        tr.commit();
    }

    public static void start(Context context, NewsType newsType) {
        Intent starter = new Intent(context, MainActivity.class);
        //对象必须可序列化
        starter.putExtra(KEY_TYPELIST, newsType);
        context.startActivity(starter);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radio0:
                //  replaceFragment(R.id.main_content, mHomeFragment);
                showFragment(mHomeFragment);
                break;
            case R.id.radio1:
                //    replaceFragment(R.id.main_content, mHotFragment);
                showFragment(mHotFragment);
                break;
            case R.id.radio2:
                //  replaceFragment(R.id.main_content, mFavorFragment);
                showFragment(mFavorFragment);
                break;
            case R.id.radio3:
                //    replaceFragment(R.id.main_content, mSettingFragment);
                showFragment(mSettingFragment);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(NetEase netEase) {

        //  Toast.makeText(this, "docId:"+docId, Toast.LENGTH_SHORT).show();
        //跳转到浏览页面，传递docId
        BrowserActivity.start(this,netEase);
    }

}
