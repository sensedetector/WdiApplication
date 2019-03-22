package sensedetector.wdi.application.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sensedetecor.mkrajewski.wdi.wdiapplication.R
import kotlinx.android.synthetic.main.duck_layout.*

class RubberDuckFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.duck_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemSquareView.setIcon(resources.getDrawable(R.drawable.rubberduck, null))
        itemSquareView.setTitle(resources.getString(R.string.rubber_duck_title))
        itemSquareView.setSubtitle(resources.getString(R.string.rubber_duck_title))
    }
}