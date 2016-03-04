package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import ca.carleton.elec3907.grouph.fpvtest.R
import ca.carleton.elec3907.grouph.fpvtest.main.OnFragmentInteractionListener
import ca.carleton.elec3907.grouph.fpvtest.widget.OnSeekBarProgressChangeListener
import ca.carleton.elec3907.grouph.fpvtest.widget.SeekBarListeners
import ca.carleton.elec3907.grouph.fpvtest.widget.SeekBarSnapHelper
import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.TimeUnit

/**
 * Created by francois on 16-02-20.
 */
class SeekBarFragment : Fragment() {
    private val rotationSeekBar by lazy { findViewById(R.id.rotation) as SeekBar }
    private val throttleSeekBar by lazy { findViewById(R.id.throttle) as SeekBar }

    private var rotation = 0
    private var throttle = 0

    private lateinit var onFragmentInteractionListener: OnFragmentInteractionListener

    private lateinit var networkSub: Subscription

    companion object {
        fun newInstance(args: Bundle): SeekBarFragment {
            val frag = SeekBarFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context !is OnFragmentInteractionListener) {
            throw IllegalArgumentException("$context must implement OnFragmentInteractionListener")
        }

        onFragmentInteractionListener = context
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_seekbar, container, false)
    }

    override fun onStart() {
        super.onStart()

        rotationSeekBar.max = 100
        val rotationListeners = SeekBarListeners(rotationSeekBar)
        val rotationSnapHelper = SeekBarSnapHelper(rotationListeners)
        rotationSnapHelper.addSnapPoint(50)
        rotationListeners.onProgressChangedListeners += object : OnSeekBarProgressChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                rotation = progress
            }
        }

        throttleSeekBar.max = 100
        val throttleListeners = SeekBarListeners(throttleSeekBar)
        val throttleSnapHelper = SeekBarSnapHelper(throttleListeners)
        throttleSnapHelper.addSnapPoint(0)
        throttleSnapHelper.addSnapPoint(10) //brake
        throttleSnapHelper.addSnapPoint(40) //1st speed
        throttleSnapHelper.addSnapPoint(70) //2nd speed
        throttleSnapHelper.addSnapPoint(100) //3rd speed
        throttleListeners.onProgressChangedListeners += object : OnSeekBarProgressChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                throttle = progress
            }
        }

        networkSub = Observable.interval(100L, TimeUnit.MILLISECONDS, Schedulers.io())
                .subscribe ({
                    val buffer = ByteBuffer.allocate(11)
                            .order(ByteOrder.LITTLE_ENDIAN)
                            .put('R'.toByte()) //rotation
                            .put((if (rotation <= 50) 'L' else 'R').toByte())
                            .putInt(Math.abs(rotation - 50))
                            .put('T'.toByte()) //throttle
                            .putInt(throttle)
                    onFragmentInteractionListener.sendToNetwork(buffer.array())
                }, { throwable ->
                    onFragmentInteractionListener.onError(throwable)
                })
    }

    override fun onStop() {
        super.onStop()
        networkSub.unsubscribe()
    }
}