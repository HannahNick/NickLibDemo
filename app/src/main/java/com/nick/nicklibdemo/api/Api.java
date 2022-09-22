package com.nick.nicklibdemo.api;

import com.nick.nicklibdemo.mvp.vo.GoodsVO;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Nick on 9/14/22 5:09 PM.
 */
public interface Api {

    /**
     * 获取搜索商品列表
     * @param body
     * @return
     */
    @POST("GOODSNOZZLE-APP-SERVICE/goodsSkuApp/queryEs.apec")
    Observable<GoodsVO> getSortList(@Body RequestBody body);
}
