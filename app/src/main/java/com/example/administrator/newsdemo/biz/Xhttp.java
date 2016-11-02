package com.example.administrator.newsdemo.biz;

import android.util.Log;
import com.example.administrator.newsdemo.common.CommonUrls;
import com.example.administrator.newsdemo.entity.NetEase;
import com.example.administrator.newsdemo.entity.NewsContent;
import com.example.administrator.newsdemo.entity.NewsType;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/10/29.
 */

public class Xhttp {
    private static final String TAG = "xhttp";

    /**
     * 获取对应分类的新闻列表
     *
     * @param listener 成功获取后解析出的集合通过接口传递
     * @param tid      新闻分类tid
     * @param uri      新闻网址
     */
    public static void getNewsList(String uri, final String tid, final OnSuccessListener listener) {
        RequestParams entity = new RequestParams(uri);
        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ArrayList<NetEase> neteaseNews = new ArrayList<>();
                Gson gson = new Gson();
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray array = obj.getJSONArray(tid);
                    for (int i = 0; i < array.length(); i++) {
                        NetEase netEase = gson.fromJson(array.get(i).toString(), NetEase.class);
                        neteaseNews.add(netEase);
                    }
                    //使用接口
                    if (listener != null) {
                        listener.setNewsList(neteaseNews);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: " + ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

                Log.d(TAG, "onCancelled: ");
            }

            @Override
            public void onFinished() {

                Log.d(TAG, "onFinished: ");
            }
        };
        x.http().get(entity, callback);
    }

    /**
     * 新闻获取成功的接口
     */
    public interface OnSuccessListener {
        void setNewsList(List<NetEase> neteaseNews);
    }

    /**
     * 获取新闻分类列表
     *
     * @param listener 获取成功后解析的对象通过接口传递
     */
    public static void getNewsTypeList(final NewsTypeListener listener) {
        RequestParams entity = new RequestParams(CommonUrls.NEWS_TYPE);

        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                NewsType newsType = gson.fromJson(result, NewsType.class);

                //1 gson解析
                //2.用接口传递集合对象
                if (listener != null) {
                    listener.onSuccess(newsType);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.d(TAG, "onError: ");
            }

            @Override
            public void onCancelled(CancelledException cex) {

                Log.d(TAG, "onCancelled: ");
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "onFinished: ");
                //用接口通知activity去跳转
                if (listener != null) {
                    listener.onFinish();
                }
            }
        };
        x.http().get(entity, callback);
    }

    /**
     * 分类接口的监听：
     */
    public interface NewsTypeListener {
        /**
         * 成功获取分类对象，数据将通过intent进行activity间的传递
         *
         * @param newsType
         */
        void onSuccess(NewsType newsType);

        /**
         * 执行网络获取完成，activity即将跳转
         */
        void onFinish();
    }

    /**
     * 获取新闻内容
     *
     * @param docId    新闻文档id，由分类列表中的单条数据中可获取到
     * @param listener 重整成功后用接口传递最后的完整string
     */
    public static void getNewsContent(final String docId, final NewsContentListener listener) {

        RequestParams entity = new RequestParams(CommonUrls.getNewsContentUrl(docId));
        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String string = obj.getString(docId);
                    Gson gson = new Gson();
                    NewsContent newsContent = gson.fromJson(string, NewsContent.class);

                    //从newsContent对象中把body和img集合重新整合一个让webview显示的string

                    String before = "<p><img src=\"";
                    String after = "\"/> </img></p>";
                    //重整字符串：
                    //1.添加标题；<p><h1>  </h1></p>
                    String title_b = "<p><h2>";
                    String title_a = "</h2></p>";

                    newsContent.body = title_b + newsContent.title + title_a + newsContent.body;
                    //添加作者：
                    for (NewsContent.Img img : newsContent.img) {
                        newsContent.body = newsContent.body.replace(img.ref, before + img.src + after);
                    }

                    //字符串交给webview
                    if (listener != null) {
                        listener.onFinish(newsContent.body);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onSuccess解析异常: " + e.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG, "onCancelled: ");
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "onFinished: ");

            }
        };
        x.http().get(entity, callback);

    }

    /**
     * 新闻内容的获取监听
     */
    public interface NewsContentListener {
        void onFinish(String str);
    }
}
