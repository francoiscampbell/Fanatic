package io.github.francoiscampbell.manytimes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import ca.carleton.elec3907.grouph.fpvtest.main.fragment.OrientationFragment
import ca.carleton.elec3907.grouph.fpvtest.main.fragment.SeekBarFragment

/**
 * Created by francois on 2016-01-14.
 */
class MainActivityPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val fragmentInits = arrayOf(
            { OrientationFragment.newInstance(Bundle.EMPTY) },
            { SeekBarFragment.newInstance(Bundle.EMPTY) })

    private val pageTitles = arrayOf(
            "Orientation",
            "SeekBar")

    override fun getItem(position: Int): Fragment? {
        val fragment = fragmentInits[position]()
        return fragment
    }

    override fun getCount(): Int {
        return fragmentInits.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitles[position]
    }
}