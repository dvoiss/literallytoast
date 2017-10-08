package com.dvoiss.literallytoast

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.media.AudioManager
import android.media.MediaPlayer


internal object ToastUtils {
  fun playToasterSound(context: Context) {
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0)
    val mp = MediaPlayer.create(context, R.raw.toaster)
    mp.setOnCompletionListener { it.release() }
    mp.start()
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0)
  }

  /**
   * This method is referenced from "French Toast", a different toast library
   * https://github.com/pyricau/frenchtoast.
   */
  fun findParentActivity(context: Context): Activity {
    val appContext = context.applicationContext
    var unwrapped: Context? = context
    while (true) {
      if (unwrapped is Activity) {
        return unwrapped
      }

      if (unwrapped == null || unwrapped === appContext || unwrapped !is ContextWrapper ||
          unwrapped.baseContext === unwrapped) {
        throw FindActivityException(context)
      }

      unwrapped = unwrapped.baseContext
    }
  }

  internal class FindActivityException(context: Context) : IllegalArgumentException(
      "Could not find Activity from context: " + context)
}
