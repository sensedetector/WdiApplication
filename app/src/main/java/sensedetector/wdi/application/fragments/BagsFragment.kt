package sensedetector.wdi.application.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sensedetector.wdi.application.views.ItemLinearView
import sensedetecor.mkrajewski.wdi.wdiapplication.R
import kotlinx.android.synthetic.main.bags_layout.*

class BagsFragment: Fragment() {

    private val bagsIcons = arrayOf(
        R.drawable.backpack,
        R.drawable.rubberduck,
        R.drawable.bumble,
        R.drawable.unicorn
    )

    private val bagsTitles = arrayOf(
        "Bags",
        "Toys",
        "Transformers",
        "Beautiful unicorns"
    )

    private val bagsSubtitles = arrayOf(
        "Hold things",
        "You can play with it",
        "Have a lot of moving parts",
        "Yes they are beautiful!"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bags_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0 until itemList.childCount) {
            val item = itemList.getChildAt(i) as ItemLinearView
            item.setIcon(resources.getDrawable(bagsIcons[i], null))
            item.setTitle(bagsTitles[i])
            item.setSubtitle(bagsSubtitles[i])
            item.setSize(resources.getDimensionPixelSize(R.dimen.item_size))
        }
    }
}