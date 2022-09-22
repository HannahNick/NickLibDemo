package com.nick.nicklibdemo.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nick.nicklibdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 2022/9/20 11:25.
 */
public class HomeListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeListAdapter() {
        super(R.layout.item_home_list, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
    }
}
