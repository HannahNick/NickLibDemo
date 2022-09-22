package com.nick.nicklibdemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nick.nicklib.manager.ImageLoaderManager;
import com.nick.nicklib.util.ZeroCancelUtil;
import com.nick.nicklibdemo.R;
import com.nick.nicklibdemo.mvp.vo.GoodsVO;

import java.util.ArrayList;

/**
 * Created by Nick on 9/14/22 12:25 PM.
 */
public class GoodsListAdapter extends BaseQuickAdapter<GoodsVO.DataBean.RowsBean, BaseViewHolder> {

    public GoodsListAdapter() {
        super(R.layout.item_goodslist, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsVO.DataBean.RowsBean item) {
        ImageLoaderManager.getInstance()
                .loadUrl(mContext,helper.getView(R.id.iv_goods_img),item.getSkuImage());
        helper.setText(R.id.tv_goods_name,item.getGoodsName())
                .setText(R.id.tv_goods_spec,item.getSkuName())
                .setText(R.id.tv_goods_price, ZeroCancelUtil.getInstance().formatBigDecimal(item.getPrice()))
                .addOnClickListener(R.id.iv_cart);
    }
}
