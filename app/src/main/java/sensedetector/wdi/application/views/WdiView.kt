package sensedetector.wdi.application.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import sensedetecor.mkrajewski.wdi.wdiapplication.R

class WdiView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var fillColor: Int = 0
    private var strokeColor: Int = 0

    private var size: Float = 0f
    private var thickness: Float = 0f
    private var notch: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private lateinit var masterPath: Path

    private val ripples: MutableList<Ripple> = mutableListOf()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.WdiView,
            0, 0).apply {

            try {
                fillColor = getColor(R.styleable.WdiView_fillColor,
                    ContextCompat.getColor(context, R.color.colorPrimary))
                strokeColor = getColor(R.styleable.WdiView_strokeColor,
                    ContextCompat.getColor(context, R.color.colorAccent))
                strokePaint.strokeWidth = getFloat(R.styleable.WdiView_strokeThickness,
                    defaultStrokeWidth
                )
            } finally {
                recycle()
            }
        }
    }

    private val defaultFillColor = fillColor
    private val defaultStrokeColor = strokeColor

    fun ripple(strokeColor: Int = defaultStrokeColor, fillColor: Int = defaultFillColor) {
        val animator = ValueAnimator()
        val ripple = Ripple(strokeColor, fillColor, animator)
        animator.addUpdateListener {
            ripple.scale = it.animatedValue as Float
            this@WdiView.invalidate()
        }
        animator.addListener(object: Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                this@WdiView.fillColor = fillColor
                this@WdiView.strokeColor = strokeColor
                ripples.remove(ripple)
                this@WdiView.invalidate()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                ripples.add(ripple)
            }

        })
        animator.interpolator = AccelerateInterpolator(2f)
        animator.setFloatValues(0.0f, 1f)
        animator.duration = 2000L
        animator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var size = 0

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            widthSize = Int.MAX_VALUE
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = Int.MAX_VALUE
        }

        if (widthMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.EXACTLY) {
            size = Math.min(widthSize, heightSize)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            size = Math.max(minimumSize, widthSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            size = Math.max(minimumSize, heightSize)
        } else {
            size = minimumSize
        }

        // must call this
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val xpad = (paddingLeft + paddingRight).toFloat()
        val ypad = (paddingTop + paddingBottom).toFloat()

        val width = w.toFloat() - xpad
        val height = h.toFloat() - ypad

        size = Math.min(width, height)
        thickness = size / 5
        notch = size / 2 - thickness

        masterPath = makePath()

        val rectF = RectF()
        masterPath.computeBounds(rectF, true)
        centerX = rectF.centerX()
        centerY = rectF.centerY()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            drawPath(masterPath, strokeColor, fillColor)
            for (ripple in ripples) {
                drawPath(ripple.scaledPath, ripple.strokeColor, ripple.fillColor)
            }
        }
    }

    private fun Canvas.drawPath(path: Path, strokeColor: Int, fillColor: Int) {
        fillPaint.color = fillColor
        strokePaint.color = strokeColor
        drawPath(path, fillPaint)
        drawPath(path, strokePaint)
    }

    private fun makePath(): Path {
        return with(Path()) {
            moveTo(thickness, size)
            lineTo(size / 2, size - notch)
            lineTo(size - thickness, size)
            lineTo(size, size - thickness)
            lineTo(size - notch, size / 2)
            lineTo(size, thickness)
            lineTo(size - thickness, 0f)
            lineTo(size / 2, notch)
            lineTo(thickness, 0f)
            lineTo(0f, thickness)
            lineTo(notch, size / 2)
            lineTo(0f, size - thickness)
            close()
            offset(paddingLeft.toFloat(), paddingTop.toFloat())
            this
        }
    }

    private inner class Ripple(
        val strokeColor: Int,
        val fillColor: Int,
        val animator: ValueAnimator) {

        var scaledPath = Path()

        var scale: Float = 1.0f
            set(value) {
                field = value
                val scaleMatrix = Matrix()
                scaleMatrix.setScale(scale, scale, centerX, centerY)
                masterPath.transform(scaleMatrix, scaledPath)
            }
    }

    private companion object {
        private const val minimumSize = 300
        private const val defaultStrokeWidth = 10f
    }
}