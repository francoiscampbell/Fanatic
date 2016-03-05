package ca.carleton.elec3907.grouph.fpvtest.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import ca.carleton.elec3907.grouph.fpvtest.R
import ca.carleton.elec3907.grouph.fpvtest.ext.loge
import io.github.francoiscampbell.manytimes.MainActivityPagerAdapter

class MainActivity : AppCompatActivity(), MainView, OnFragmentInteractionListener {

    private val presenter = MainPresenterImpl() //inject this as singleton


    private val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }
    private val tabLayout by lazy { findViewById(R.id.tabLayout) as TabLayout }
    private val viewPager by lazy { findViewById(R.id.frag_placeholder) as ViewPager }
    private val pagerAdapter = MainActivityPagerAdapter(supportFragmentManager)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this)
    }

    override fun sendToNetwork(data: ByteArray) {
        presenter.sendToNetwork(data)
    }

     override fun onError(t: Throwable) {
         loge("Error", t)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}

interface OnFragmentInteractionListener {
    fun sendToNetwork(data: ByteArray)
    fun onError(t: Throwable)
}
