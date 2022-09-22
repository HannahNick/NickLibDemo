package com.nick.nicklibdemo.mvp.view;

import com.nick.nicklibdemo.mvp.vo.GoodsVO;

import java.util.List;

/**
 * Created by Nick on 9/14/22 5:31 PM.
 */
public interface GoodsListView {
    void getGoodsList(List<GoodsVO.DataBean.RowsBean> rowsBeanList);
}
