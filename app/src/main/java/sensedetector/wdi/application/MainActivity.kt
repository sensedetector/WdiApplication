package sensedetector.wdi.application

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import sensedetector.wdi.application.fragments.BagsFragment
import sensedetector.wdi.application.fragments.RubberDuckFragment
import sensedetector.wdi.application.fragments.TransformerFragment
import sensedetector.wdi.application.fragments.UnicornFragment
import kotlinx.android.synthetic.main.activity_main.*
import sensedetecor.mkrajewski.wdi.wdiapplication.R

class MainActivity : FragmentActivity() {

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_bags -> {
                    goToFragment(BagsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_toys -> {
                    goToFragment(RubberDuckFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_transformers -> {
                    goToFragment(TransformerFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dragons -> {
                    goToFragment(UnicornFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToFragment(BagsFragment())

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
