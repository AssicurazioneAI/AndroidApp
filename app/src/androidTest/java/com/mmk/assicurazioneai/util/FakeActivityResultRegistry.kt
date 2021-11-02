package com.mmk.assicurazioneai.util

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityOptionsCompat

/**
 * This class is will be used only for testing purposes.
 * Purpose of this class is to provide any expectedResult
 * through ActivityResultRegistry
 */

class FakeActivityResultRegistry<O : Any?> : ActivityResultRegistry() {

    var expectedResult: Uri? = null
    var isResultFailed = false
    override fun <I : Any?, O> onLaunch(
        requestCode: Int,
        contract: ActivityResultContract<I, O>,
        input: I,
        options: ActivityOptionsCompat?
    ) {

        val resultCode = if (isResultFailed) RESULT_CANCELED else RESULT_OK
        dispatchResult(
            requestCode,
            resultCode,
            Intent().also { it.data = expectedResult })
    }



}
