package com.nick.nicklib.base.presenter;


import com.nick.nicklib.eventbus.RxBus;

import io.reactivex.disposables.Disposable;

/**
 * Created by Nick on 2017/10/7.
 */

public abstract class BasePresenter {

    /**
     * 发送错误信息到统一处理
     * @param e
     */
    public void sendError(Throwable e){
        RxBus.getInstance().post(e);
    }

    /**
     * 释放资源
     * @param d
     */
    public void release(Disposable... d){
        for (Disposable disposable : d) {
            if (disposable!=null&&!disposable.isDisposed()){
                disposable.dispose();
            }
        }
    }

    public abstract void clear();
}
