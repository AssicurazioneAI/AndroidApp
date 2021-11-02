package com.mmk.assicurazioneai.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context?.toast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    this?.let {
        if (text.isNullOrEmpty().not())
            Toast.makeText(it, text, duration).show()
    }

}

fun Context?.toast(@StringRes textResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    this?.let {
        val text = getString(textResId)
        if (text.isEmpty().not())
            Toast.makeText(it, text, duration).show()
    }

}