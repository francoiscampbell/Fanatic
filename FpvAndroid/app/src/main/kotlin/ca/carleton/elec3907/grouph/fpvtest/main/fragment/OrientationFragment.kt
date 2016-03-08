package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ca.carleton.elec3907.grouph.fpvtest.R
import ca.carleton.elec3907.grouph.fpvtest.ext.findViewById
import ca.carleton.elec3907.grouph.fpvtest.main.OnFragmentInteractionListener
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.TimeUnit

/**
 * Created by francois on 16-02-20.
 */
class OrientationFragment : Fragment() {
    private val presenter = OrientationPresenterImpl() //inject this as singleton

    private val txtRotation by lazy { findViewById(R.id.txtRotation) as TextView }
    private val txtX by lazy { findViewById(R.id.txtX) as TextView }
    private val txtY by lazy { findViewById(R.id.txtY) as TextView }
    private val txtZ by lazy { findViewById(R.id.txtZ) as TextView }

    private lateinit var uiSub: Subscription
    private lateinit var networkSub: Subscription

    private lateinit var onFragmentInteractionListener: OnFragmentInteractionListener

    companion object {
        fun newInstance(args: Bundle): OrientationFragment {
            val frag = OrientationFragment()
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
        return inflater?.inflate(R.layout.fragment_orientation, container, false)
    }

    override fun onStart() {
        super.onStart()

        val rotation = activity.windowManager.defaultDisplay.rotation
        txtRotation.text = presenter.getRotationText(rotation)

        val orientation = presenter.getOrientation(activity.application, rotation)

        //move rx stuff to presenter

        //make presenter call method on view to set text
        uiSub = orientation
                .observeOn(AndroidSchedulers.mainThread())
                .onBackpressureDrop()
                .subscribe { orientation ->
                    txtX.text = orientation.x.toString()
                    txtY.text = orientation.y.toString()
                    txtZ.text = orientation.z.toString()
                }

        networkSub = orientation
                .observeOn(Schedulers.io())
                .throttleFirst(100, TimeUnit.MILLISECONDS)
                .subscribe({ orientation ->
                    val Throttle = CalculateThrottlePower(orientation)
                    val Rotation = CalculateRotation(orientation)
                    val buffer = ByteBuffer.allocate(12)
                            .order(ByteOrder.LITTLE_ENDIAN)
                            .put('R'.toByte()) //rotation
                            .put((if (Rotation.toInt() <= 50) 'L' else 'R').toByte())
                            .putInt(Math.abs(Rotation.toInt() - 50))
                            .put('T'.toByte()) //throttle
                            .putInt(Throttle.toInt())
                    onFragmentInteractionListener.sendToNetwork(buffer.array())
                }, { throwable ->
                    onFragmentInteractionListener.onError(throwable)
                })


    }

    //Convert the coordinates in to magnitude of throttle, or rotation
    fun CalculateThrottlePower(Orientation: OrientationSensor.Orientation): Double {
        var Throttlepower: Double = (((((Orientation.x * 1000) * -1) + 1.5) * (2 / 3)) * 100)
        return Throttlepower
    }

    fun CalculateRotation(Orientation: OrientationSensor.Orientation): Double {
        // Calculates Rotation In Degrees (either positive or n
        var Rotation: Float = ((Orientation.x * 1000) * (1 / 3) * 100) + 50
        return Rotation.toDouble()
    }

    override fun onStop() {
        super.onStop()

        uiSub.unsubscribe()
        networkSub.unsubscribe()
    }
}