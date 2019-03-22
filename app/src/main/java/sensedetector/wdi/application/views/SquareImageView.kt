package sensedetector.wdi.application.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView

class SquareImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    private val debugTag
        get() = "${javaClass.simpleName}:${tag?.toString() ?: ""}"

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d(debugTag,
            "onMeasure: [${MeasureSpec.toString(widthMeasureSpec)}," +
                    " ${MeasureSpec.toString(heightMeasureSpec)}]")

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val size: Int
        val intrinsicSize = Math.max(drawable.intrinsicWidth,
            drawable.intrinsicHeight)

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            widthSize = Int.MAX_VALUE
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = Int.MAX_VALUE
        }

        if (widthMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.EXACTLY) {
            size = Math.min(widthSize, heightSize)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            size = Math.min(intrinsicSize, widthSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            size = Math.min(intrinsicSize, heightSize)
        } else {
            size = intrinsicSize
        }

        Log.d(debugTag, "onMeasure: exit with size = $size")

        // must call this
        setMeasuredDimension(size, size)
    }
}