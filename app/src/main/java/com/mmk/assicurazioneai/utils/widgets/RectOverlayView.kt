package com.mmk.assicurazioneai.utils.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.mmk.assicurazioneai.R
import com.mmk.domain.model.CarDamage

class RectOverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val rectangleCoordinates: MutableList<RectF> = mutableListOf()
    private val mSeverityList: MutableList<String> = mutableListOf()


    private val paint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = ContextCompat.getColor(context!!, R.color.greenAlpha)
        strokeWidth = 10f
    }

    fun drawRectBounds(rectBounds: List<RectF>, severityList: List<String>) {
        rectangleCoordinates.clear()
        rectangleCoordinates.addAll(rectBounds)

        mSeverityList.clear()
        mSeverityList.addAll(severityList)

        invalidate()
    }

    fun clear() {
        rectangleCoordinates.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rectangleCoordinates.forEachIndexed { index, rectF ->
            paint.color = when (mSeverityList[index]) {
                CarDamage.DamagedPart.SEVERITY_LIGHT -> ContextCompat.getColor(
                    context!!,
                    R.color.greenAlpha
                )
                CarDamage.DamagedPart.SEVERITY_MEDIUM -> ContextCompat.getColor(
                    context!!,
                    R.color.yellowAlpha
                )
                else -> ContextCompat.getColor(context!!, R.color.redAlpha)
            }
            canvas?.drawRect(rectF, paint)

        }
    }

}