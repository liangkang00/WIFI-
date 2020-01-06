package com.ssv.ssvwifitool.chip.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.ssv.ssvwifitool.R;
public class ScalButton extends Button {
    private ObjectAnimator objectAnimator;

    public static final int STATE_PLAYING =1;//正在播放
    public static final int STATE_PAUSE =2;//暂停
    public static final int STATE_STOP =3;//停止
    public int state;

    public ScalButton(Context context) {
        super(context);
        init();
    }

    public ScalButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScalButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        state = STATE_STOP;
        objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);//添加旋转动画，旋转中心默认为控件中点
        objectAnimator.setDuration(3000);//设置动画时间
        objectAnimator.setInterpolator(new LinearInterpolator());//动画时间线性渐变
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
    }

    public void startScal(){
        if(state == STATE_STOP){
            objectAnimator.start();//动画开始
            state = STATE_PLAYING;
        }else if(state == STATE_PAUSE){
            objectAnimator.resume();//动画重新开始
            state = STATE_PLAYING;
        }else if(state == STATE_PLAYING){
            objectAnimator.pause();//动画暂停
            state = STATE_PAUSE;
        }
    }

    public void stopScal(){
        objectAnimator.end();//动画结束
        state = STATE_STOP;
    }
}

