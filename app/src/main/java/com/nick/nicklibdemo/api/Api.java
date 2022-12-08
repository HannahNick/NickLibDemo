package com.nick.nicklibdemo.api;

import com.nick.nicklibdemo.mvp.vo.GoodsVO;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

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


    @GET
    Observable<String> getGoogleToBaidu(@Url String url);
}
