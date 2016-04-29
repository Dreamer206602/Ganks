package com.mx.ganks.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.mx.ganks.R;

/**
 * Created by boobooL on 2016/4/29 0029
 * Created 邮箱 ：boobooMX@163.com
 */

/**
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能
 */
public class RefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    //滑动到最下面时的上拉动作
    private int mTouchSlop;

    /**
     * ListView的实例
     */
    private ListView mListView;


    /**
     * 上拉监听器，到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;


    /**
     * ListView的加载中的Footer
     */
    private View mListViewFooter;

    /**
     * 按下时的Y坐标
     */
    private int mYDown;

    /**
     * 抬起时的Y坐标，与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;

    /**
     * 是否是在加载中（上拉加载更多）
     */
    private boolean isLoading = false;


    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //初始化ListView
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取ListView的对象
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                //设置滚动监听给ListView，使得滚动的情况下也可以自动加载
                mListView.setOnScrollListener(this);
            }
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下
                mYDown= (int) ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                //移动
                mLastY= (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                //抬起
                if(canLoad()){
                    loadData();
                }
                break;

        }


        return super.dispatchTouchEvent(ev);
    }



    /**
     * 是否可以加载更多，条件是
     * 到了最底部，
     * 而且ListView不在加载中，
     * 且为上拉的动作
     * @return
     */
    private boolean canLoad(){
        return  isBottom()&& !isLoading&&isPullUp();
    }


    /**
     * 判断是否到了底部
     * @return
     */
    public boolean isBottom(){
        if(mListView!=null&&mListView.getAdapter()!=null){
            return mListView.getLastVisiblePosition()==(mListView.getAdapter().getCount()-1);
        }
        return false;
    }

    /**
     * 判断是否是上拉操作
     * @return
     */
    public boolean isPullUp(){
        return  (mYDown-mLastY)>=mTouchSlop;
    }


    /**
     * 如果是到了最底部，而且是上拉的操作，那么执行onLoad方法
     */
    private void loadData() {
        if(mOnLoadListener!=null){
            //设置状态
            setLoading(true);
            mOnLoadListener.onLoad();
        }

    }

    /**
     * 设置装填
     * @param loading
     */
    private void setLoading(boolean loading) {
        isLoading=loading;
        if(isLoading){
            mListView.addFooterView(mListViewFooter);
        }else{
            mListView.removeFooterView(mListViewFooter);
            mYDown=0;
            mLastY=0;
        }

    }

    public OnLoadListener getOnLoadListener() {
        return mOnLoadListener;
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
    }

    /**
     * 以下两个方法是  ListView的滚动事件的监听回调
     *
     * @param view
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        //滑动到了最底部也可以加载更多
        if(canLoad()){
            loadData();
        }
    }
}
