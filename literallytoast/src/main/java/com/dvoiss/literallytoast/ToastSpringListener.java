package com.dvoiss.literallytoast;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringUtil;

class ToastSpringListener extends SimpleSpringListener {

    @NonNull private final View view;
    @NonNull private final WindowManager windowManager;
    @NonNull private final Handler handler;
    private final long duration;

    ToastSpringListener(@NonNull View view, @NonNull WindowManager windowManager,
        @NonNull Handler handler, long duration) {
        this.view = view;
        this.windowManager = windowManager;
        this.handler = handler;
        this.duration = duration;
    }

    @Override
    public void onSpringUpdate(@NonNull Spring spring) {
        float value = (float) spring.getCurrentValue();
        float barPosition =
            (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, view.getHeight(), 0);
        view.setTranslationY(barPosition);
    }

    @Override
    public void onSpringAtRest(@NonNull final Spring spring) {
        if (spring.getEndValue() == 1) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    spring.setEndValue(0);
                }
            }, duration);
        } else if (spring.getEndValue() <= 0.01) {
            view.setVisibility(View.GONE);
            windowManager.removeView(view);
        }
    }
}
