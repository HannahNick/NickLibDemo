package com.nick.nicklib.base.presenter;

/**
 * Created by Nick on 2019-06-25.
 */
public abstract class BaseListPresenter2 extends BasePresenter {

    protected int mCurrentNo = 1;

    public void loadMore(){
        mCurrentNo++;
        requestListData();
    }

    public void refresh(){
        mCurrentNo = 1;
        requestListData();
    }

    protected abstract void requestListData();



}
