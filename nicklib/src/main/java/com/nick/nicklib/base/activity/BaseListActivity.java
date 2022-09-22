package com.nick.nicklib.base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.nick.nicklib.base.presenter.BaseListPresenter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by Nick on 2018/7/18.
 * 含有刷新和加载更多的页面
 */
public abstract class BaseListActivity extends BaseActivity implements OnLoadmoreListener, OnRefreshListener {

    protected SmartRefreshLayout mSrl_refreshLayout;
    private BaseListPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
    }

    /**
     *  初始化刷新控件
     * @param enableRefresh
     * @param enableLoadMore
     */
    protected void initRefreshView(boolean enableRefresh,boolean enableLoadMore){
        initRefreshLayout();
        setEnableRefresh(enableRefresh);
        setEnableLoadMore(enableLoadMore);
    }

    /**
     * 是否能够刷新
     * @param enableRefresh
     */
    protected void setEnableRefresh(boolean enableRefresh){
        mSrl_refreshLayout.setEnableRefresh(enableRefresh);
    }

    /**
     * 是否能够加载
     * @param enableLoadMore
     */
    protected void setEnableLoadMore(boolean enableLoadMore){
        mSrl_refreshLayout.setEnableLoadmore(enableLoadMore);
    }

    /**
     * 是否显示列表内容
     */
    protected void showList(boolean show){
        if (show){
            mSrl_refreshLayout.setVisibility(View.VISIBLE);
        }else {
            mSrl_refreshLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 停止加载更多
     */
    protected void finishLoadMore(){
        mSrl_refreshLayout.finishLoadmore();
    }

    /**
     * 停止刷新
     */
    protected void finishRefresh(){
        mSrl_refreshLayout.finishRefresh();
        resetNoMoreData();
    }

    /**
     * 没有更多数据
     */
    protected void noMoreData(){
        mSrl_refreshLayout.setLoadmoreFinished(true);
        finishLoadMore();
    }

    /**
     * 重置没有更多数据
     */
    protected void resetNoMoreData(){
        mSrl_refreshLayout.resetNoMoreData();
    }

    /**
     * 下拉刷新上拉加载
     */
    private void initRefreshLayout() {
        mSrl_refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(false));
        mSrl_refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mSrl_refreshLayout.setEnableHeaderTranslationContent(false);
        mSrl_refreshLayout.setOnLoadmoreListener(this);
        mSrl_refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.onLoadMore();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.onRefresh();
    }

    /**
     * 获取Activity的Presenter
     * @return 继承BaseListPresenter的Presenter
     */
    public abstract BaseListPresenter getPresenter();
}
