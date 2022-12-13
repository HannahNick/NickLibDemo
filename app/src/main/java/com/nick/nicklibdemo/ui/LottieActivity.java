package com.nick.nicklibdemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.blankj.utilcode.util.ResourceUtils;
import com.nick.nicklibdemo.R;

public class LottieActivity extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);

        lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.setAnimation(R.raw.no_sign);
    }


    private void addListener(){

    }

    public void play(View view){
        lottieAnimationView.playAnimation();
    }

    public void stop(View view){
        lottieAnimationView.pauseAnimation();
    }

    public void resume(View view){
        lottieAnimationView.resumeAnimation();
    }
}