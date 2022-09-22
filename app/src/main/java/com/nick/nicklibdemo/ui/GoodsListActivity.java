package com.nick.nicklibdemo.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.LogUtils;
import com.nick.nicklib.base.activity.BaseUIActivity;
import com.nick.nicklib.util.ListAdapterUtil;
import com.nick.nicklibdemo.R;
import com.nick.nicklibdemo.adapter.GoodsListAdapter;
import com.nick.nicklibdemo.manager.UserManager;
import com.nick.nicklibdemo.mvp.presenter.GoodsListPresenter;
import com.nick.nicklibdemo.mvp.view.GoodsListView;
import com.nick.nicklibdemo.mvp.vo.GoodsVO;

import java.util.List;

/**
 * Created by Nick on 9/14/22 9:49 AM.
 */
public class GoodsListActivity extends BaseUIActivity implements GoodsListView {

    /**
     * Ui
     */
    private RecyclerView mRv_goods_list;
    private SwipeRefreshLayout mSrl_refreshlayout;


    /**
     * Data
     */
    private GoodsListAdapter mGoodsListAdapter;

    /**
     * presenter
     */
    private GoodsListPresenter mPresenter;


    @Override
    protected int setLayout() {
        return R.layout.activity_goodslist;
    }

    @Override
    protected void findViews() {
        mRv_goods_list = findViewById(R.id.rcv_goods_list);
        mSrl_refreshlayout = findViewById(R.id.srl_refreshlayout);
    }

    @Override
    protected void init() {
        initListView();
        mPresenter = new GoodsListPresenter(this);
        mPresenter.refresh();
        LogUtils.i(UserManager.getInstance().getName());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void release() {
    }

    @SuppressLint("ShowToast")
    private void initListView(){
        mGoodsListAdapter = new GoodsListAdapter();
//        mRv_goods_list.addItemDecoration(new GridSpacingItemDecoration(2,getResources().getDimensionPixelSize(R.dimen.item_short_margin),true,true));
        mRv_goods_list.setAdapter(mGoodsListAdapter);
        mGoodsListAdapter.setOnItemClickListener((adapter, view, position) -> {
            Toast.makeText(this,String.format("点击了第%1$s行",position),Toast.LENGTH_LONG).show();
        });
        mGoodsListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            int id = view.getId();
            if (id == R.id.iv_cart) {
                Toast.makeText(this, String.format("点击了第%1$s行的购物车", position), Toast.LENGTH_LONG).show();
            }
        });
        mGoodsListAdapter.setOnLoadMoreListener(()->{
            mPresenter.loadMore();
        },mRv_goods_list);
        mSrl_refreshlayout.setOnRefreshListener(() -> {
            mPresenter.refresh();
        });
    }

    @Override
    public void getGoodsList(List<GoodsVO.DataBean.RowsBean> rowsBeanList) {
        ListAdapterUtil.setUpData(mSrl_refreshlayout,mGoodsListAdapter,rowsBeanList,null);
    }
}
