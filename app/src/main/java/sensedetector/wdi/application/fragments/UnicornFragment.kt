package sensedetector.wdi.application.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sensedetecor.mkrajewski.wdi.wdiapplication.R
import kotlinx.android.synthetic.main.unicorn_layout.*
import kotlin.random.Random

class UnicornFragment: Fragment() {

    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.unicorn_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors = intArrayOf(
            requireContext().getColor(R.color.colorPrimary),
            requireContext().getColor(R.color.colorAccent)
        )

        makeRippleButton.setOnClickListener {
            val color = randomColor()
            val fill = randomColor()
            wdiView.ripple(color, fill)
        }
    }

    fun randomColor(): Int {
        val rgb = List(3) { Random.nextInt(0, 256)}
        return Color.argb(255, rgb[0], rgb[1], rgb[2])
    }
}