package com.nick.nicklibdemo.ui;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;

import com.nick.nicklib.base.activity.BaseActivity;
import com.nick.nicklibdemo.R;

/**
 * Created by Nick on 2022/9/22 14:39.
 */
public class VectorDrawableActivity extends BaseActivity {
    static {
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_vector_drawable;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void release() {

    }

    public void anim(View view){
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
    }
}
