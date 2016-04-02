package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import android.app.Application
import android.view.Surface
import ca.carleton.elec3907.grouph.fpvtest.sensor.OrientationSensor
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by francois on 16-03-04.
 */
class OrientationPresenterImpl : OrientationPresenter() {
    private var view: OrientationView? = null

    private lateinit var uiSub: Subscription
    private lateinit var networkSub: Subscription

    override fun onStart(view: OrientationView) {
        super.onStart(view)
        this.view = view
    }

    override fun startOrientationListener(application: Application, screenRotation: Int) {
        val orientation = OrientationSensor.getOrientation(application, screenRotation).publish()

        uiSub = orientation
                .observeOn(AndroidSchedulers.mainThread())
                .onBackpressureDrop()
                .subscribe { orientation ->
                    view?.textX = orientation.x.toString()
                    view?.textY = orientation.y.toString()
                    view?.textZ = orientation.z.toString()
                }

        networkSub = orientation
                .observeOn(Schedulers.io())
                .throttleFirst(100, TimeUnit.MILLISECONDS)
                .subscribe({ orientation ->
                    sendToNetwork(byteArrayOf(calculateThrottle(orientation), calculateRotation(orientation)))
                }, { throwable ->
                    onError(throwable)
                })

        orientation.connect()
    }

    override fun getRotationText(rotation: Int): String = when (rotation) {
        Surface.ROTATION_0 -> "ROTATION_0"
        Surface.ROTATION_90 -> "ROTATION_90"
        Surface.ROTATION_180 -> "ROTATION_180"
        Surface.ROTATION_270 -> "ROTATION_270"
        else -> "Unknown rotation"
    }

    override fun onStop() {
        super.onStop()
        view = null
        uiSub.unsubscribe()
        networkSub.unsubscribe()
    }

    //Convert the coordinates in to magnitude of throttle, or rotation
    fun calculateThrottle(orientation: OrientationSensor.Orientation): Byte {
        val throttle = ((((orientation.x * 1000) * -1) + 1.5) * (2 / 3)) * 100
        return Math.min(Math.max(throttle, 0.0), 255.0).toByte()
    }

    // Calculates Rotation In Degrees (either positive or negative)
    fun calculateRotation(orientation: OrientationSensor.Orientation): Byte {
        val rotation = (orientation.x * 1000) * (1 / 3) * 100
        return Math.min(Math.max(rotation, 0f), 255f).toByte()
    }
}
