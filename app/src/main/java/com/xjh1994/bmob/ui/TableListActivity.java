package com.xjh1994.bmob.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.xjh1994.bmob.adapter.BaseUltimateAdapter;
import com.xjh1994.bmob.base.BaseListActivity;
import com.xjh1994.bmob.base.IAdapterItem;
import com.xjh1994.bmob.adapter.TableItem;
import com.xjh1994.bmob.service.BmobAppInfoManger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by XJH on 16/3/28.
 * Blog: http://www.xjh1994.com
 */
public class TableListActivity extends BaseListActivity {

    public static final String EXTRA_APPID = "appId";
    public static final String EXTRA_MASTERKEY = "masterKey";

    private String appId;
    private String masterKey;

    public static void start(Context context, String appId, String masterKey) {
        Intent intent = new Intent();
        intent.setClass(context, TableListActivity.class);
        intent.putExtra(EXTRA_APPID, appId);
        intent.putExtra(EXTRA_MASTERKEY, masterKey);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appId = getIntent().getStringExtra(EXTRA_APPID);
        masterKey = getIntent().getStringExtra(EXTRA_MASTERKEY);
    }

    @Override
    protected void onLoadNew() {

    }

    @Override
    protected void onLoadMore() {

    }

    @Override
    protected void fetchData() {


        BmobAppInfoManger bmobAppInfoManger = new BmobAppInfoManger(appId, masterKey);
        bmobAppInfoManger.getAppInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String bmobAppInfo) {
                        Logger.d(bmobAppInfo);
                        List<String> classList = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(bmobAppInfo);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject resultsObj = jsonArray.getJSONObject(0);
                                String className = resultsObj.getString("className");    //表名
                                classList.add(className);
                            }
                            adapter = new BaseUltimateAdapter(TableListActivity.this, classList) {
                                @NonNull
                                @Override
                                public IAdapterItem createItem(Object type) {
                                    return new TableItem();
                                }
                            };
                            ultimateRecyclerView.setAdapter(adapter);

                            /*JSONObject fieldsObj = resultsObj.getJSONObject("fields");    //fields

                            Iterator iterator = fieldsObj.keys();
                            List<String> fieldsKeyList = new ArrayList<>();
                            while (iterator.hasNext())
                                fieldsKeyList.add(iterator.next().toString());
                            int size = fieldsKeyList.size();
                            for (int i = 0; i < size; i++) {
                                JSONObject obj = fieldsObj.getJSONObject(fieldsKeyList.get(i));
                                String type = obj.getString("type");
                                Logger.d(fieldsKeyList.get(i) + ":" + type);
                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
