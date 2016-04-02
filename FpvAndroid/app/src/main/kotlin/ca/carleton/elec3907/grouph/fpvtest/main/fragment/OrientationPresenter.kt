package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import android.app.Application
import ca.carleton.elec3907.grouph.fpvtest.main.CommonPresenterImpl

/**
 * Created by francois on 16-03-04.
 */
abstract class OrientationPresenter : CommonPresenterImpl<OrientationView>() {
    abstract fun startOrientationListener(application: Application, screenRotation: Int)
    abstract fun getRotationText(rotation: Int): String
}

