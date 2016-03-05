package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import android.app.Application
import android.view.Surface
import ca.carleton.elec3907.grouph.fpvtest.sensor.OrientationSensor
import rx.Observable

/**
 * Created by francois on 16-03-04.
 */
class OrientationPresenterImpl : OrientationPresenter {
    private lateinit var view: OrientationView

    override fun onStart(view: OrientationView) {
        this.view = view
    }

    override fun getOrientation(application: Application, rotation: Int): Observable<OrientationSensor.Orientation> {
        return OrientationSensor.getOrientation(application, rotation)
    }

    override fun getRotationText(rotation: Int): String = when (rotation) {
        Surface.ROTATION_0 -> "ROTATION_0"
        Surface.ROTATION_90 -> "ROTATION_90"
        Surface.ROTATION_180 -> "ROTATION_180"
        Surface.ROTATION_270 -> "ROTATION_270"
        else -> "Unknown rotation"
    }
}
