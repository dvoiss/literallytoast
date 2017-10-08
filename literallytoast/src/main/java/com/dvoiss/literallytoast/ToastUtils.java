package com.dvoiss.literallytoast;

import android.graphics.PixelFormat;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;

class ToastUtils {
    @NonNull
    static WindowManager.LayoutParams createWindowManagerParams() {
        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        layoutParams.gravity = Gravity.CENTER;
        return layoutParams;
    }
}
