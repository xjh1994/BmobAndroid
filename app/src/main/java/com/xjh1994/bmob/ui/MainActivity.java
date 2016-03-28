package com.xjh1994.bmob.ui;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.xjh1994.bmob.R;
import com.xjh1994.bmob.adapter.AppItem;
import com.xjh1994.bmob.adapter.BaseUltimateAdapter;
import com.xjh1994.bmob.base.BaseActivity;
import com.xjh1994.bmob.base.IAdapterItem;
import com.xjh1994.bmob.bean.BmobApp;
import com.xjh1994.bmob.bean.Results;
import com.xjh1994.bmob.service.BmobAppManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.update.BmobUpdateAgent;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.ultimate_recycler_view)
    UltimateRecyclerView ultimateRecyclerView;

    protected LinearLayoutManager linearLayoutManager;
    protected List dataList = new ArrayList<>();
    protected BaseUltimateAdapter adapter;

    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initViews() {
        initDrawer();
        ultimateRecyclerView = (UltimateRecyclerView) findViewById(com.xjh1994.bmob.R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        initUpdate();
        fetchData();
    }

    private void fetchData() {
        ultimateRecyclerView.showEmptyView();
        BmobAppManger bmobAppManger = new BmobAppManger("977135148@qq.com", "www.xjh1994.com");
        bmobAppManger.getApps()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BmobApp>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(final BmobApp bmobApp) {
                        List<Results> resultsList = bmobApp.getResults();
                        dataList.addAll(resultsList);
                        adapter = new BaseUltimateAdapter(MainActivity.this, dataList) {
                            @NonNull
                            @Override
                            public IAdapterItem createItem(Object type) {
                                return new AppItem();
                            }
                        };
                        ultimateRecyclerView.setAdapter(adapter);
                        ultimateRecyclerView.hideEmptyView();
                    }
                });
    }

    /**
     * 自动更新
     */
    private void initUpdate() {
        BmobUpdateAgent.setUpdateCheckConfig(false);
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        BmobUpdateAgent.update(this);
    }

    protected void initDrawer() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, getTheme()));
        } else {
            drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_one:
                        toolbar.setTitle(R.string.title_menu_1);
                        break;
                    case R.id.item_two:
                        toolbar.setTitle(R.string.title_menu_2);
                        break;
                    case R.id.item_three:
                        toolbar.setTitle(R.string.title_menu_3);
                        break;
                }
                item.setChecked(true);//点击了把它设为选中状态
                drawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!drawerLayout.isDrawerOpen(navigationView))
                    drawerLayout.openDrawer(navigationView);
                else
                    drawerLayout.closeDrawer(navigationView);
                break;
            case R.id.setting:
                startAnimActivity(SettingActivity.class);
                break;
            case R.id.feedback:
                startAnimActivity(FeedbackActivity.class);
                break;
            case R.id.list:
                startAnimActivity(ListActivity.class);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }
}
