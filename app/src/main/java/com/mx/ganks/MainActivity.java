package com.mx.ganks;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mx.ganks.adapter.GankCommonAdapter;
import com.mx.ganks.cache.ACache;
import com.mx.ganks.callback.ICallBack;
import com.mx.ganks.constant.Constants;
import com.mx.ganks.model.CommonDate;
import com.mx.ganks.net.GankApi;
import com.mx.ganks.view.OnLoadListener;
import com.mx.ganks.view.RefreshLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.sloop.net.utils.NetUtils;
import com.sloop.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener, OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.id_listview)
    ListView mListView;
    @Bind(R.id.id_swipe_ly)
    RefreshLayout mRefreshLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private String[] flags = {
            Constants.FLAG_ALL,
            Constants.FLAG_MeiZHI,
            Constants.FLAG_Android,
            Constants.FLAG_IOS,
            Constants.FLAG_JS,
            Constants.FLAG_RECOMMEND,
            Constants.FLAG_VIDEO,
            Constants.FLAG_EXPAND};
    private String currentFlag = flags[0];
    private int currentIndex = 1;
    private ACache mACache;
    private GankCommonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mACache = ACache.get(getApplicationContext());
        initView();

        startRefresh();

    }

    private void startRefresh() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();

    }


    private void initView() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        View headerView = mNavigationView.
                inflateHeaderView(R.layout.nav_header_main);

        mNavigationView.setNavigationItemSelectedListener(this);

        headerView.findViewById(R.id.head_img).setOnClickListener(this);
        headerView.findViewById(R.id.head_web).setOnClickListener(this);
        headerView.findViewById(R.id.head_name).setOnClickListener(this);

        mRefreshLayout.setOnLoadListener(this);
        mRefreshLayout.setOnRefreshListener(this);

        mAdapter = new GankCommonAdapter(MainActivity.this, null);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, false));

    }

    private ICallBack<CommonDate> mCallBack = new ICallBack<CommonDate>() {
        @Override
        public void onSuccess(String flag, String key, CommonDate commonDate) {
            Log.d("Main", "Success");
            stopAllState();
           // if (flag != currentFlag) return;
            if (commonDate.isError()) {
                ToastUtils.show(MainActivity.this, "数据加载出错");
                return;
            }
            mACache.put(key, commonDate, ACache.TIME_DAY * 7);//存入缓存
            Log.e("Main", "key=" + key);

            List<CommonDate.ResultsEntity> datas = commonDate.getResults();
            mAdapter.addDatas(datas);

        }

        @Override
        public void onFailure(String flag, String key, String why) {

            Log.e("Main", why);
            stopAllState();
            ToastUtils.show(MainActivity.this, why);
            getDataFromCache(key);//从缓存加载数据
        }
    };

    private void getDataFromCache(String key) {
        Log.d("Main", "get data key=" + key);
        CommonDate data = (CommonDate) mACache.getAsObject(key);
        if (data != null) {
            mAdapter.addDatas(data.getResults());
            stopAllState();
        }
    }

    private void stopAllState() {
        mRefreshLayout.setLoading(false);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    //mNavigationView的点击事件的回调
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        switch (id) {
            case R.id.nav_main:
                resetAllByFlag(flags[0]);
                break;
            case R.id.nav_welfare:
                resetAllByFlag(flags[1]);
                break;
            case R.id.nav_android:
                resetAllByFlag(flags[2]);
                break;
            case R.id.nav_ios:
                resetAllByFlag(flags[3]);
                break;
            case R.id.nav_js:
                resetAllByFlag(flags[4]);
                break;
            case R.id.nav_recommend:
                resetAllByFlag(flags[5]);
                break;
            case R.id.nav_video:
                resetAllByFlag(flags[6]);
                break;
            case R.id.nav_expand:
                resetAllByFlag(flags[7]);
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_img:
                showByUrl(Constants.URL_GANK);
                break;
            case R.id.head_name:
            case R.id.head_web:
                showByUrl(Constants.URL_GITHUB);
                break;
        }

    }

    //ListView的点击事件的回调
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CommonDate.ResultsEntity data = mAdapter.getDataById(position);
        if (data.getType().equals(Constants.FLAG_MeiZHI)) {
            Intent intent = new Intent(this, ImageActivity.class);
            intent.putExtra(Constants.KEY_IMAGE, data.getUrl());
            startActivity(intent);
        } else {
            showByUrl(data.getUrl());
        }


    }


    //一下两个是SwipeRefreshLayout 的接口回调

    @Override
    public void onLoad() {

        currentIndex++;
        getData();
    }

    @Override
    public void onRefresh() {

        Log.e("Main", "onRefresh");
        currentIndex = 1;
        mAdapter.clearDatas();
        getData();

    }

    private void getData() {
        String key = currentFlag + 20 + currentIndex;
        if (NetUtils.isNetConnection(this)) {
            GankApi.getCommonData(currentFlag, 20, currentIndex, mCallBack);
        } else {
            ToastUtils.show(MainActivity.this, "网络连接异常，请检查网络");
            getDataFromCache(key);//从缓存加载数据
        }
    }

    public void showByUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void resetAllByFlag(String flag) {
        mToolbar.setTitle(flag);
        if (flag.equals(Constants.FLAG_ALL)) {
            mToolbar.setTitle(R.string.app_name);
        }
        //clear data
        currentFlag = flag;
        currentIndex = 1;
        mAdapter.clearDatas();

        if (Build.VERSION.SDK_INT >= 8) {
            mListView.smoothScrollToPosition(0);
        } else {
            mListView.setSelection(0);
        }


        //get new data
        startRefresh();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAllState();
    }
}
