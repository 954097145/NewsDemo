package com.example.administrator.newsdemo.fragment;

import com.example.administrator.newsdemo.R;
import com.example.administrator.newsdemo.activities.BrowserActivity;
import com.example.administrator.newsdemo.base.BaseFragment;
import com.example.administrator.newsdemo.biz.Xhttp;
import com.example.administrator.newsdemo.db.DBManager;
import com.example.administrator.newsdemo.entity.NetEase;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;


import butterknife.BindView;
/**
 * Created by Administrator on 2016/11/1.
 */

public class NewsContentFragment extends BaseFragment {
    @BindView(R.id.webView1)
    WebView mWebView1;
    @BindView(R.id.btn_popUp)
    ImageView mBtnPopUp;
    private String mDocId;
    private String db_str;//需要数据库保存的字符串
    private NetEase db_netEase;//数据库保存的netease对象


    public NewsContentFragment() {
    }

    public static NewsContentFragment newInstance(NetEase netEase) {
        NewsContentFragment fragment = new NewsContentFragment();
        Bundle args = new Bundle();
        args.putSerializable(BrowserActivity.KEY_NETEASE,netEase);
        args.putString(BrowserActivity.KEY_DOCID, netEase.docid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDocId = getArguments().getString(BrowserActivity.KEY_DOCID);
            db_netEase= (NetEase) getArguments().getSerializable(BrowserActivity.KEY_NETEASE);
        }
    }


    @Override
    protected void initData() {

        //初始化数据
        //从网络获取json，解析，显示
        //webview的基本设置
        /**
         * webview设置，允许javaScript脚本
         */
        mWebView1.getSettings().setJavaScriptEnabled(true);
        /**
         * 让图片自适应我们的屏幕尺寸, webview 本身可以设置setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)实现;
         但是!!! 该方法只支持4.4之前的版本.4.4以后的就不起作用了.为了能兼容所有该版本.我们通过 webview 与js 交互的方式来实现。
         */
        mWebView1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        /**
         *    //shouldOverrideUrlLoading返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
         */
        mWebView1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

            /**
             *   //重新设置图片大小，适配当前的屏幕
             *
             *
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                imgReset();
            }
        });
        /**
         * 通过工具类获取对应docid的json，并解析成string交给webview显示。
         */
        Xhttp.getNewsContent(mDocId, new Xhttp.NewsContentListener() {
            @Override
            public void onFinish(String str) {
                mWebView1.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
                //字符串保存为全局对象，需要收藏时使用。
                db_str = str;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_content;
    }

    /**
     * 重整图文混排中的字符串，将其适应屏幕
     */
    private void imgReset() {
        mWebView1.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%';   " +
                "}" +
                "})()");
    }


    @OnClick(R.id.btn_popUp)
    public void onClick() {
        //        弹出菜单：
        PopupMenu popup = new PopupMenu(getActivity(), mBtnPopUp);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popmenu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_favor:
                        //NetEase对象由点击事件和docid一并传入，或只传递netease。
                        //字符串在第一次获取过后保存了全局引用。
                        long i= DBManager.getDBManager(getContext()).insertNewsData(db_netEase,db_str);
                        if (i>0){
                            Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "收藏失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.menu_share:
                        break;
                    case R.id.menu_comment:
                        break;
                }
                return true;
            }
        });

        popup.show(); //showing popup menu
    }
}
