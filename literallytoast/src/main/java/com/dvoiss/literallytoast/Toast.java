package com.dvoiss.literallytoast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.facebook.rebound.SpringSystem;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Toast {
    @Nullable private static Typeface typeface;
    @Nullable private static WindowManager windowManager;

    @NonNull private final View view;
    @Duration private final int duration;

    @NonNull private final Handler handler;

    @SuppressLint("InflateParams")
    private Toast(@NonNull Context context, @NonNull CharSequence text, @Duration int duration) {
        this.duration = duration;
        this.view = getLayoutInflater(context).inflate(R.layout.literallytoast, null);
        this.handler = new Handler();
        setupStyledTextView(context, this.view, text);
    }

    @NonNull
    public static Toast create(@NonNull Context context, @NonNull CharSequence text,
        @Duration int duration) {
        return new Toast(context, text, duration);
    }

    @NonNull
    public static Toast create(@NonNull Context context, @StringRes int resId,
        @Duration int duration) {
        return create(context, context.getString(resId), duration);
    }

    public void show() {
        getWindowManager(view.getContext()).addView(view, ToastUtils.createWindowManagerParams());
        SpringSystem.create()
            .createSpring()
            .addListener(new ToastSpringListener(view, windowManager, handler, getDuration()))
            .setEndValue(1);
    }

    private long getDuration() {
        if (duration == LENGTH_SHORT) {
            return SHORT_DURATION_TIMEOUT;
        } else if (duration == LENGTH_LONG) {
            return LONG_DURATION_TIMEOUT;
        } else {
            return duration;
        }
    }

    private static void setupStyledTextView(@NonNull Context context, @NonNull View view,
        @NonNull CharSequence text) {
        TextView textView = view.findViewById(R.id.message);
        textView.setTypeface(getTypeface(context));
        textView.setText(text);
    }

    @NonNull
    private static LayoutInflater getLayoutInflater(@NonNull Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    private static Typeface getTypeface(@NonNull Context context) {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/yummy_bread.ttf");
        }
        return typeface;
    }

    @NonNull
    private static WindowManager getWindowManager(@NonNull Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

            if (windowManager == null) {
                throw new IllegalStateException("Could not find window manager.");
            }
        }

        return windowManager;
    }

    // region Duration IntDef

    /**
     * Duration copied from android.widget.Toast.
     */
    @IntDef({ LENGTH_SHORT, LENGTH_LONG })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {}

    /**
     * Copied from android.widget.Toast: {@link android.widget.Toast#LENGTH_SHORT}.
     */
    public static final int LENGTH_SHORT = 0;

    /**
     * Copied from android.widget.Toast: {@link android.widget.Toast#LENGTH_LONG}.
     */
    public static final int LENGTH_LONG = 1;

    /**
     * Copied from android.widget.Toast.
     */
    static final long SHORT_DURATION_TIMEOUT = 4000;

    /**
     * Copied from android.widget.Toast.
     */
    static final long LONG_DURATION_TIMEOUT = 7000;

    // endregion
}
