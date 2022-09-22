package com.nick.nicklibdemo.ui;


import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.nick.nicklib.base.activity.BaseUIActivity;
import com.nick.nicklib.eventbus.RxBus;
import com.nick.nicklib.util.SPUtil;
import com.nick.nicklibdemo.R;
import com.nick.nicklibdemo.adapter.HomeListAdapter;
import com.nick.nicklibdemo.exception.ApiException;
import com.nick.nicklibdemo.manager.UserManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseUIActivity {

    private Disposable mDisNetWork;//网络异常等错误订阅
    private RecyclerView mRv_list;

    private HomeListAdapter mAdapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        mRv_list = findViewById(R.id.rv_list);
    }

    @Override
    protected void init() {
        initNetWorkSubscription();
        initList();
        initUserManager();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void release() {

    }

    private void initUserManager(){
        UserManager.getInstance().setName("nick");
    }

    private void initList(){
        mAdapter = new HomeListAdapter();
        mRv_list.setAdapter(mAdapter);
        List<String> listData = new ArrayList<>();
        listData.add("GoodsListActivity");
        listData.add("ServiceActivity");
        listData.add("NewProcessActivity");
        listData.add("VectorDrawableActivity");
        mAdapter.replaceData(listData);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = null;
            switch (position){
                case 0:
                    intent = new Intent(this,GoodsListActivity.class);
                    break;
                case 1:
                    intent = new Intent(this,ServiceActivity.class);
                    break;
                case 2:
                    intent = new Intent(this,NewProcessActivity.class);
                    intent.putExtra(NewProcessActivity.SM_CODE,"123");
//                    startActivityForResult(intent,NewProcessActivity.REQUEST_CODE);
                    break;
                case 3:
                    intent = new Intent(this,VectorDrawableActivity.class);
                    break;
            }
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NewProcessActivity.REQUEST_CODE && resultCode == NewProcessActivity.RESULT_CODE){
            assert data != null;
            String stringExtra = data.getStringExtra(NewProcessActivity.SM_CODE);
            LogUtils.i(stringExtra);
        }
    }

    /**
     * 订阅网络错误
     */
    private void initNetWorkSubscription(){
        mDisNetWork = RxBus.getInstance()
                .toObservable(Throwable.class)
                .subscribe(this::NetWorkErrorMsg, this::NetWorkErrorMsg);
    }

    /**
     * 网络异常统一处理
     * @param e
     */
    private void NetWorkErrorMsg(Throwable e){
        if (e instanceof ConnectException || e instanceof HttpException) {
            Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
        } else if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
            Toast.makeText(getApplicationContext(), "连接超时", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ApiException) {
            String code = ((ApiException) e).getErrorCode();
            String msg = ((ApiException) e).getMessage();
            switch (code) {
                case "600001":
                    SPUtil.getInstance().remove(SPUtil.TOKEN);
                    SPUtil.getInstance().remove(SPUtil.LAST_LOGIN);
                    SPUtil.getInstance().remove(SPUtil.USER_ID);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}