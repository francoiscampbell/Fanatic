package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import android.app.Application
import ca.carleton.elec3907.grouph.fpvtest.abs.Presenter
import ca.carleton.elec3907.grouph.fpvtest.sensor.OrientationSensor
import rx.Observable

/**
 * Created by francois on 16-03-04.
 */
interface OrientationPresenter : Presenter<OrientationView> {
    fun getOrientation(application: Application, rotation: Int): Observable<OrientationSensor.Orientation>
    fun getRotationText(rotation: Int): String
}

