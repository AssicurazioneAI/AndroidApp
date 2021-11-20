package com.mmk.assicurazioneai.utils

import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.lifecycle.*
import timber.log.Timber

/**
 * Created by mirzemehdi on 4/25/21
 */
class KeyboardEventListener(
    private val view: View,
    private val onKeyboardChanged: (isOpen: Boolean, keyboardHeight: Int) -> Unit

) : LifecycleObserver {


    private val listener = ViewTreeObserver.OnGlobalLayoutListener {
        val r = Rect()
        view.getWindowVisibleDisplayFrame(r)
        val height = view.context.resources.displayMetrics.heightPixels
        val diff = height - r.bottom
        if (diff != 0) {
            addOrRemoveKeyboardPadding(true, diff)
            onKeyboardChanged.invoke(true, diff)


        } else {
            addOrRemoveKeyboardPadding(false, 0)
            onKeyboardChanged.invoke(false, 0)

        }
    }

    init {
        view.findViewTreeLifecycleOwner()?.lifecycle?.addObserver(this)
        registerKeyboardListener()
    }

    private fun registerKeyboardListener() {
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_PAUSE)
    @CallSuper
    fun onLifecyclePause() {
        view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }


    @Synchronized
    private fun addOrRemoveKeyboardPadding(isKeyboardOpen: Boolean, keyboardHeight: Int) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) return //Android 11 handles this padding default
        if (isKeyboardOpen) {
            if (view.translationY.toInt() != keyboardHeight) {
//                view.setPadding(0, 0, 0, keyboardHeight)
                view.translationY = -keyboardHeight.toFloat()
            }
        } else {
            if (view.translationY.toInt() != 0) {
                view.translationY = 0f
//                view.setPadding(0, 0, 0, 0)
            }
        }
    }
}