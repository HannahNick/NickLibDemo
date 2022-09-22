package com.nick.nicklib.manager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nick on 2017/9/7.
 * 图片加载
 */

public class ImageLoaderManager {


    public static ImageLoaderManager getInstance() {
        return Holder.sManager;
    }


    public void loadUrl(Context context, ImageView iv, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (url.endsWith(".gif")){
//            loadGif(context,iv,url,false);
        }else {
            GlideApp.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv);
        }
    }

    public void loadUrl(Context context, String url, CustomTarget<Drawable> target) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        GlideApp.with(context)
                .load(url)
                .into(target);
    }

    public void loadDropImg(Context context, String url, CustomTarget<Drawable> target) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        GlideApp.with(context)
                .load(url)
                .transform(new GlideCircleTransform())
                .dontAnimate()
                .into(target);
    }

    public void loadRes(Context context, ImageView iv, int res) {
        GlideApp.with(context)
                .load(res)
                .into(iv);
    }

    public void loadUrlDefault(Context context, ImageView iv, String url, int resId) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        GlideApp.with(context)
                .load(url)
                .placeholder(resId)
                .into(iv);
    }

    public void loadUrlByError(Context context, ImageView iv, String url){
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .error(R.drawable.error_img)
                .into(iv);
    }

//    public void loadGif(Context context, ImageView iv, String url, boolean isFullScreen){
//        Disposable subscribe = Observable.create(
//                (ObservableOnSubscribe<File>) e -> e.onNext(GlideApp.with(context)
//                        .asFile()
//                        .load(url)
//                        .submit()
//                        .get()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(file -> {
//                    GifDrawable gifDrawable = new GifDrawable(file);
//                    if (isFullScreen){
//                        int intrinsicHeight = gifDrawable.getIntrinsicHeight();
//                        int intrinsicWidth = gifDrawable.getIntrinsicWidth();
//                        int width = iv.getWidth();
//                        iv.getLayoutParams().height = width*intrinsicHeight/intrinsicWidth;
//                        iv.setBackground(gifDrawable);
//                    }else {
//                        iv.setImageDrawable(gifDrawable);
//                    }
//                },throwable -> {
//
//                });
//    }

    public void loadUrlCircle(Context context, ImageView iv, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        GlideApp.with(context)
                .load(url)
                .transform(new GlideCircleTransform())
                .into(iv);
    }


    public void loadAny(Context context, ImageView iv, Object url){
        if (url!=null){
            GlideApp.with(context)
                    .load(url)
                    .into(iv);
        }
    }

    private static class Holder {
        private static final ImageLoaderManager sManager = new ImageLoaderManager();
    }
}
