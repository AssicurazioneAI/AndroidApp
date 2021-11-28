package com.mmk.assicurazioneai.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

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

fun Fragment.showSnackbar(
    text: CharSequence?,
    duration: Int = BaseTransientBottomBar.LENGTH_SHORT
) {
    view?.let { v ->
        if (text.isNullOrEmpty().not()) {
            val snackbar = Snackbar.make(v, text!!, duration)
            snackbar.show()
        }
    }
}


fun Fragment.showSnackbar(
    @StringRes textResId: Int,
    duration: Int = BaseTransientBottomBar.LENGTH_SHORT
) {
    view?.let { v ->
        val text = getString(textResId)
        if (text.isEmpty().not()) {
            val snackbar = Snackbar.make(v, text, duration)
            snackbar.show()
        }
    }
}


fun Activity.setTranslucentStatusBar() {

    window.statusBarColor = Color.TRANSPARENT
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val flags =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = flags
    } else {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
    }
}

fun Fragment.showKeyboard(view: View) {
    context?.let {
        val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Fragment.hasPermission(permission: String) = ContextCompat.checkSelfPermission(
    requireContext(),
    permission
) == PackageManager.PERMISSION_GRANTED

