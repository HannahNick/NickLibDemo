package com.nick.nicklib.base.presenter;


import com.nick.nicklib.constants.Constants;

import java.util.WeakHashMap;

/**
 * Created by Nick on 2018/7/16.
 * 分页请求专业Presenter
 */
public abstract class BaseListPresenter extends BasePresenter {

    private int mCurrentPage = 1;
    private int mPageSize = Constants.PAGESIZE;
    private int mMaxPageSize = 1;

    /**
     * Activity或Fragment调用加载更多
     */
    public void onLoadMore(){
        mCurrentPage++;
        if (mCurrentPage>mMaxPageSize){
            noMoreData();
        }else {
            doLoadMore(getNewParams());
        }
    }

    /**
     * Activity或Fragment调用刷新
     */
    public void onRefresh(){
        mCurrentPage=1;
        doRefresh(getNewParams());
    }

    /**
     * 刷新或者加载时再次封装请求数据
     * @return 封装数据
     */
    private WeakHashMap<String, Object> getNewParams(){
        WeakHashMap<String, Object> newParams = getParams();
        newParams.put("currentNo",mCurrentPage);
        newParams.put("pageSize",mPageSize);
        return newParams;
    }

    /**
     * 设置最大页数
     * @param maxPageSize 最大页数
     */
    protected void setMaxPageSize(int maxPageSize){
        mMaxPageSize = maxPageSize;
    }

    /**
     * 没有更多数据
     */
    protected abstract void noMoreData();

    /**
     * 刷新
     * @param newParams 刷新所需数据
     */
    protected abstract void doRefresh(WeakHashMap<String, Object> newParams);

    /**
     * 加载更多
     * @param newParams 加载更多所需数据
     */
    protected abstract void doLoadMore(WeakHashMap<String, Object> newParams);

    /**
     * 分页必传参数带进来 注意:不要把页码相关参数传进来
     * @return 请求参数
     */
    protected abstract WeakHashMap<String, Object> getParams();
}
