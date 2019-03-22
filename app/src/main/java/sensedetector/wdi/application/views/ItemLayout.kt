package sensedetector.wdi.application.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class ItemLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private lateinit var squareView: View

    private lateinit var descriptionView: View

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (childCount != 2) {
            throw IllegalStateException("ItemLayout should have exactly two children")
        }

        squareView = getChildAt(0)
        descriptionView = getChildAt(1)
    }

    /**
     * Validates if a set of layout parameters is valid for a child of this ViewGroup.
     */
    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is ViewGroup.MarginLayoutParams
    }

    /**
     * @return A set of default layout parameters when given a child with no layout parameters.
     */
    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * @return A set of layout parameters created from attributes passed in XML.
     */
    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }

    /**
     * Called when [.checkLayoutParams] fails.
     *
     * @return A set of valid layout parameters for this ViewGroup that copies appropriate/valid
     * attributes from the supplied, not-so-good parameters.
     */
    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return generateDefaultLayoutParams()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // Measure description view first time to get height.
        var heightMeasured: Int
        do {
            val lp = descriptionView.layoutParams as ViewGroup.MarginLayoutParams
            heightMeasured = descriptionView.measuredHeight + lp.topMargin + lp.bottomMargin
            measureChildWithMargins(descriptionView, widthMeasureSpec, heightMeasured, heightMeasureSpec, 0)
        } while (heightMeasured != descriptionView.measuredHeight + lp.topMargin + lp.bottomMargin)

        var lp = squareView.layoutParams as ViewGroup.MarginLayoutParams

        // Measure square view
        measureChildWithMargins(
            squareView,
            // Pass width constraints and width already used.
            MeasureSpec.makeMeasureSpec(heightMeasured - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY), 0,
            // Pass height constraints and height already used.
            MeasureSpec.makeMeasureSpec(heightMeasured - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY), 0
        )

        // Calculate this view's measured width and height.

        // Figure out how much total space the icon used.
        lp = squareView.layoutParams as ViewGroup.MarginLayoutParams
        val iconWidth = squareView.measuredWidth + lp.leftMargin + lp.rightMargin
        val iconHeight = squareView.measuredHeight + lp.topMargin + lp.bottomMargin

        // Figure out how much total space the title used.
        lp = descriptionView.layoutParams as ViewGroup.MarginLayoutParams
        val titleWidth = descriptionView.measuredWidth + lp.leftMargin + lp.rightMargin
        val titleHeight = descriptionView.measuredHeight + lp.topMargin + lp.bottomMargin


        // The width taken by the children + padding.
        val width = paddingTop + paddingBottom +
                iconWidth + titleWidth
        // The height taken by the children + padding.
        val height = paddingTop + paddingBottom +
                Math.max(iconHeight, titleHeight)

        // Reconcile the measured dimensions with the this view's constraints and
        // set the final measured width and height.
        setMeasuredDimension(
            View.resolveSize(width, widthMeasureSpec),
            View.resolveSize(height, heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var layoutParams = squareView.layoutParams as ViewGroup.MarginLayoutParams

        // Figure out the x-coordinate and y-coordinate of the icon.
        var x = paddingLeft + layoutParams.leftMargin
        var y = paddingTop + layoutParams.topMargin

        // Layout the icon.
        squareView.layout(x, y, x + squareView.measuredWidth, y + squareView.measuredHeight)

        // Calculate the x-coordinate of the title: icon's right coordinate +
        // the icon's right margin.
        x += squareView.measuredWidth + layoutParams.rightMargin

        // Add in the title's left margin.
        layoutParams = descriptionView.layoutParams as ViewGroup.MarginLayoutParams
        x += layoutParams.leftMargin

        // Calculate the y-coordinate of the title: this ViewGroup's top padding +
        // the title's top margin
        y = paddingTop + layoutParams.topMargin

        // Layout the title.
        descriptionView.layout(x, y, x + descriptionView.measuredWidth, y + descriptionView.measuredHeight)
    }
}
