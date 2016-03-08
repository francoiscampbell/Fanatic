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

/**
 * Created by francois on 16-02-20.
 */
class OrientationFragment : Fragment(), OrientationView {
    private val presenter = OrientationPresenterImpl() //inject this as singleton

    private val txtRotation by lazy { findViewById(R.id.txtRotation) as TextView }

    private val txtX by lazy { findViewById(R.id.txtX) as TextView }
    private val txtY by lazy { findViewById(R.id.txtY) as TextView }
    private val txtZ by lazy { findViewById(R.id.txtZ) as TextView }

    override var setTextX: CharSequence
        get() = txtX.text
        set(value) {
            txtX.text = value
        }

    override var setTextY: CharSequence
        get() = txtY.text
        set(value) {
            txtY.text = value
        }
    override var setTextZ: CharSequence
        get() = txtZ.text
        set(value) {
            txtY.text = value
        }

    companion object {
        fun newInstance(args: Bundle): OrientationFragment {
            val frag = OrientationFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_orientation, container, false)
    }

    override fun onStart() {
        super.onStart()

        val rotation = activity.windowManager.defaultDisplay.rotation
        txtRotation.text = presenter.getRotationText(rotation)
    }

    override fun onStop() {
        super.onStop()
    }
}
