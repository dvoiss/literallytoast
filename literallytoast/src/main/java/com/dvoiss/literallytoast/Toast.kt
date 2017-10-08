package com.dvoiss.literallytoast

import android.content.Context
import android.graphics.Typeface
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.facebook.rebound.SpringSystem


class Toast private constructor(context: Context, text: CharSequence, private val duration: Int,
    private var shouldPlaySound: Boolean) {

  private val view: View
  private val handler: Handler

  init {
    this.view = getLayoutInflater(context).inflate(R.layout.literallytoast, null)
    this.handler = Handler()
    setupStyledTextView(context, this.view, text)
  }

  fun show() {
    

    getWindowManager(view.context).addView(view, ToastUtils.createWindowManagerParams())
    SpringSystem.create()
        .createSpring()
        .addListener(
            ToastSpringListener(view, windowManager!!, handler, getDuration())).endValue = 1.0

    if (shouldPlaySound) {
      val audioManager = view.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
      val originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
      audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0)
      val mp = MediaPlayer.create(view.context, R.raw.toaster)
      mp.setOnCompletionListener { it.release() }
      mp.start()
      audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0)
    }
  }

  fun setPlaySound(shouldPlaySound: Boolean): Toast {
    this.shouldPlaySound = shouldPlaySound
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
    private var windowManager: WindowManager? = null

    @JvmStatic
    @JvmOverloads
    fun create(context: Context, text: CharSequence, duration: Int = LENGTH_SHORT): Toast =
        Toast(context, text, duration, shouldPlaySound = true)

    @JvmStatic
    @JvmOverloads
    fun create(context: Context, @StringRes resId: Int,
        duration: Int = LENGTH_SHORT): Toast = create(context, context.getString(resId), duration)

    private fun getTypeface(context: Context): Typeface = typeface ?: Typeface.createFromAsset(
        context.assets, "fonts/yummy_bread.ttf")

    private fun getWindowManager(context: Context): WindowManager {
      if (windowManager == null) {
        windowManager = context.applicationContext
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (windowManager == null) {
          throw IllegalStateException("Could not find window manager.")
        }
      }

      return windowManager as WindowManager
    }

    // region The values in this region were copied from android.widget.Toast.

    const val LENGTH_SHORT = 0
    const val LENGTH_LONG = 1
    internal val SHORT_DURATION_TIMEOUT: Long = 4000
    internal val LONG_DURATION_TIMEOUT: Long = 7000

    // endregion
  }
}
