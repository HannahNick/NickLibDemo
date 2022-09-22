package com.nick.nicklibdemo.mvp.presenter;

import android.text.TextUtils;

import com.nick.nicklib.base.presenter.BaseListPresenter2;
import com.nick.nicklib.util.ListAdapterUtil;
import com.nick.nicklibdemo.http.HttpManager;
import com.nick.nicklibdemo.mvp.view.GoodsListView;
import com.nick.nicklibdemo.util.RequestBodyUtil;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by Nick on 9/14/22 4:44 PM.
 */
public class GoodsListPresenter extends BaseListPresenter2 {

    private Disposable mSub_list;
    private GoodsListView mView;

    public GoodsListPresenter(GoodsListView goodsListView) {
        mView = goodsListView;

    }

    @Override
    protected void requestListData() {
        WeakHashMap<String,Object> params = new WeakHashMap<>();
        params.put("queryString", "白糖");
//        if (!TextUtils.isEmpty(mShopId)){
//            params.put("shopId",mShopId);
//        }
        params.put("cityIds", "100");
        params.put("deliveryTemplateStreetId","10034");
        params.put("currentNo",mCurrentNo);
        params.put("pageSize", ListAdapterUtil.PAGE_SIZE);
        RequestBody requestBody = RequestBodyUtil.getInstance().getRequestBody(params);
        mSub_list = HttpManager.getInstance()
                .getApi()
                .getSortList(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goodsVO -> {
                    mView.getGoodsList(goodsVO.getData().getRows());
                }, this::sendError);

    }

    @Override
    public void clear() {
        mSub_list.dispose();
    }
}
