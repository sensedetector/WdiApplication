package sensedetector.wdi.application.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import sensedetecor.mkrajewski.wdi.wdiapplication.R
import kotlinx.android.synthetic.main.item_square_view_layout.view.*

class ItemSquareView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = HORIZONTAL
        inflate(context, R.layout.item_square_view_layout, this)
    }

    fun setIcon(drawable: Drawable) = icon.setImageDrawable(drawable)

    fun setTitle(text: String) {
        title.text = text
    }

    fun setSubtitle(text: String) {
        subtitle.text = text
    }
}