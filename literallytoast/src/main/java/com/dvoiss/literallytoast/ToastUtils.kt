package com.dvoiss.literallytoast

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager

internal object ToastUtils {
  fun createWindowManagerParams(): WindowManager.LayoutParams {
    val layoutParams = WindowManager.LayoutParams()
    with(layoutParams) {
      height = WindowManager.LayoutParams.MATCH_PARENT
      width = WindowManager.LayoutParams.MATCH_PARENT
      flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
      format = PixelFormat.TRANSLUCENT
      type = WindowManager.LayoutParams.TYPE_TOAST
      gravity = Gravity.CENTER
    }
    return layoutParams
  }
}
