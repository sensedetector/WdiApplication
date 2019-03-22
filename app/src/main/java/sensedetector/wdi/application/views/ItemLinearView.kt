package sensedetector.wdi.application.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import sensedetecor.mkrajewski.wdi.wdiapplication.R
import kotlinx.android.synthetic.main.item_linear_view_layout.view.*

class ItemLinearView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        this.orientation = HORIZONTAL
        inflate(context, R.layout.item_linear_view_layout, this)
    }

    fun setIcon(drawable: Drawable) = icon.setImageDrawable(drawable)

    fun setTitle(text: String) {
        title.text = text
    }

    fun setSubtitle(text: String) {
        subtitle.text = text
    }

    fun setSize(side: Int) {
        icon.layoutParams = LayoutParams(side, side)
    }
}