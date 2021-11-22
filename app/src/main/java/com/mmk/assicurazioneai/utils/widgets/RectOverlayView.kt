package com.mmk.assicurazioneai.utils.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.mmk.assicurazioneai.R

class RectOverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val rectangleCoordinates: MutableList<RectF> = mutableListOf()

    private val paint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = ContextCompat.getColor(context!!, R.color.greenAlpha)
        strokeWidth = 10f
    }

    fun drawRectBounds(rectBounds: List<RectF>) {
        rectangleCoordinates.clear()
        rectangleCoordinates.addAll(rectBounds)
        invalidate()
    }

    fun clear(){
        rectangleCoordinates.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rectangleCoordinates.forEach { canvas?.drawRect(it, paint) }
    }

}