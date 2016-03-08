package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import ca.carleton.elec3907.grouph.fpvtest.R
import ca.carleton.elec3907.grouph.fpvtest.ext.findViewById
import ca.carleton.elec3907.grouph.fpvtest.widget.SeekBarMultiListeners
import ca.carleton.elec3907.grouph.fpvtest.widget.SeekBarSnapHelper

/**
 * Created by francois on 16-02-20.
 */
class SeekBarFragment : Fragment(), SeekBarView {
    private val presenter = SeekBarPresenterImpl()

    private val rotationSeekBar by lazy { findViewById(R.id.rotation) as SeekBar }
    private val throttleSeekBar by lazy { findViewById(R.id.throttle) as SeekBar }

    override var rotation = 0
    override var throttle = 0

    companion object {
        fun newInstance(args: Bundle): SeekBarFragment {
            val frag = SeekBarFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_seekbar, container, false)
    }

    override fun onStart() {
        super.onStart()

        presenter.onStart(this)

        rotationSeekBar.max = 100
        val rotationListeners = SeekBarMultiListeners(rotationSeekBar)
        val rotationSnapHelper = SeekBarSnapHelper(rotationListeners)
        rotationSnapHelper.addSnapPoint(50)
        rotationListeners.onProgressChangedListeners += { seekBar, progress, fromUser ->
            rotation = progress
        }

        throttleSeekBar.max = 100
        val throttleListeners = SeekBarMultiListeners(throttleSeekBar)
        val throttleSnapHelper = SeekBarSnapHelper(throttleListeners)
        throttleSnapHelper.addSnapPoint(0)
        throttleSnapHelper.addSnapPoint(10) //brake
        throttleSnapHelper.addSnapPoint(40) //1st speed
        throttleSnapHelper.addSnapPoint(70) //2nd speed
        throttleSnapHelper.addSnapPoint(100) //3rd speed
        throttleListeners.onProgressChangedListeners += { seekBar, progress, fromUser ->
            throttle = progress
        }

        presenter.startSeekBarListener()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}