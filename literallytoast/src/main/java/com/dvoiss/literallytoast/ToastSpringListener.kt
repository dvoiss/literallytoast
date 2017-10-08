package com.dvoiss.literallytoast

import android.os.Handler
import android.view.View
import android.view.WindowManager
import com.facebook.rebound.SimpleSpringListener
import com.facebook.rebound.Spring
import com.facebook.rebound.SpringUtil

internal class ToastSpringListener(private val view: View, private val windowManager: WindowManager,
    private val handler: Handler, private val duration: Long) : SimpleSpringListener() {

  /**
   * Update the y position of the toast as the spring updates.
   */
  override fun onSpringUpdate(spring: Spring) {
    view.translationY = SpringUtil.mapValueFromRangeToRange(spring.currentValue, 0.0, 1.0,
        view.height.toDouble(), 0.0).toFloat()
  }

  /**
   * If the spring is at rest and the end-value is 1 then the toast has animated onto the screen
   * and we went to remove it after a certain time.
   *
   * Otherwise if the end-value is at 0 we want to remove it right away.
   */
  override fun onSpringAtRest(spring: Spring) {
    if (spring.endValue == 1.0) {
      handler.postDelayed({ spring.endValue = 0.0 }, duration)
    } else if (spring.endValue <= 0.01) {
      view.visibility = View.GONE
      windowManager.removeView(view)
    }
  }
}
