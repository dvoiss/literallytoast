package com.dvoiss.literallytoast

import android.content.Context
import android.graphics.Typeface
import android.os.Handler
import android.support.annotation.StringRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager.LayoutParams
import android.widget.PopupWindow
import android.widget.TextView
import com.facebook.rebound.SpringSystem


class LitToast private constructor(context: Context, text: CharSequence, private val duration: Int,
    private var shouldPlayToasterSound: Boolean) {

  private val view: View
  private val handler: Handler

  init {
    this.view = getLayoutInflater(context).inflate(R.layout.literallytoast, null)
    this.handler = Handler()
    setupStyledTextView(context, this.view, text)
  }

  fun show() {
    val activity = ToastUtils.findParentActivity(view.context)
    val rootView: View = activity.window.decorView.findViewById(android.R.id.content)
    val pw = PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    pw.showAtLocation(rootView, Gravity.CENTER, 0, 0)

    SpringSystem.create()
        .createSpring()
        .addListener(ToastSpringListener(view, pw, handler, getDuration()))
        .endValue = 1.0

    if (shouldPlayToasterSound) {
      ToastUtils.playToasterSound(view.context)
    }
  }

  fun setPlayToasterSound(shouldPlayToasterSound: Boolean): LitToast {
    this.shouldPlayToasterSound = shouldPlayToasterSound
    return this
  }

  private fun getDuration(): Long {
    return if (duration == LENGTH_SHORT) {
      SHORT_DURATION_TIMEOUT
    } else if (duration == LENGTH_LONG) {
      LONG_DURATION_TIMEOUT
    } else {
      duration.toLong()
    }
  }

  private fun setupStyledTextView(context: Context, view: View, text: CharSequence) {
    val textView = view.findViewById<TextView>(R.id.message)
    textView.typeface = getTypeface(context)
    textView.text = text
  }

  private fun getLayoutInflater(context: Context): LayoutInflater = context.getSystemService(
      Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

  companion object {
    private var typeface: Typeface? = null

    @JvmStatic
    @JvmOverloads
    fun create(context: Context, text: CharSequence, duration: Int = LENGTH_SHORT): LitToast =
        LitToast(context, text, duration, shouldPlayToasterSound = true)

    @JvmStatic
    @JvmOverloads
    fun create(context: Context, @StringRes resId: Int, duration: Int = LENGTH_SHORT): LitToast =
        create(context, context.getString(resId), duration)

    private fun getTypeface(context: Context): Typeface {
      if (typeface == null) {
        typeface = Typeface.createFromAsset(context.assets, "fonts/yummy_bread.ttf")
      }

      return typeface as Typeface
    }

    // region The values in this region were copied from android.widget.Toast.

    const val LENGTH_SHORT = 0
    const val LENGTH_LONG = 1
    internal val SHORT_DURATION_TIMEOUT: Long = 4000
    internal val LONG_DURATION_TIMEOUT: Long = 7000

    // endregion
  }
}
