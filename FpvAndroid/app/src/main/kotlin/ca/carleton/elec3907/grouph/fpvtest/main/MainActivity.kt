package ca.carleton.elec3907.grouph.fpvtest.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import ca.carleton.elec3907.grouph.fpvtest.R
import ca.carleton.elec3907.grouph.fpvtest.ext.loge
import ca.carleton.elec3907.grouph.fpvtest.main.fragment.OrientationFragment
import ca.carleton.elec3907.grouph.fpvtest.main.fragment.SeekBarFragment

class MainActivity : AppCompatActivity(), MainView, OnFragmentInteractionListener {

    private val presenter = MainPresenterImpl() //inject this as singleton

    private val btnUseOrientation by lazy { findViewById(R.id.btnUseOrientation) as Button }
    private val btnUseSeekBar by lazy { findViewById(R.id.btnUseSeekBar) as Button }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this)

        btnUseOrientation.setOnClickListener {
            supportFragmentManager!!
                    .beginTransaction()
                    .replace(R.id.container, OrientationFragment.newInstance(Bundle.EMPTY))
                    .commit()
        }

        btnUseSeekBar.setOnClickListener {
            supportFragmentManager!!
                    .beginTransaction()
                    .replace(R.id.container, SeekBarFragment.newInstance(Bundle.EMPTY))
                    .commit()
        }
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
