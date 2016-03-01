package ca.carleton.elec3907.grouph.fpvtest.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.view.Surface
import com.github.pwittchen.reactivesensors.library.ReactiveSensorFilter
import com.github.pwittchen.reactivesensors.library.ReactiveSensors
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by francois on 2016-01-28.
 */
class OrientationSensor() {
    data class Orientation(val x: Float, val y: Float, val z: Float)

    companion object {
        fun getOrientation(context: Context, rotation: Int): Observable<Orientation> {
            val accel = ReactiveSensors(context).observeSensor(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL)
                    .subscribeOn(Schedulers.computation())
                    .filter(ReactiveSensorFilter.filterSensorChanged())
                    .onBackpressureDrop()
                    .observeOn(AndroidSchedulers.mainThread());
            val accelSub = accel.subscribe()

            val compass = ReactiveSensors(context).observeSensor(Sensor.TYPE_MAGNETIC_FIELD, SensorManager.SENSOR_DELAY_NORMAL)
                    .subscribeOn(Schedulers.computation())
                    .filter(ReactiveSensorFilter.filterSensorChanged())
                    .onBackpressureDrop()
                    .observeOn(AndroidSchedulers.mainThread());
            val compassSub = compass.subscribe()

            return Observable.zip(accel, compass) { accel, compass ->
                val rotationMatrix = FloatArray(9)
                SensorManager.getRotationMatrix(rotationMatrix, null, accel.sensorEvent.values, compass.sensorEvent.values)

                val rotationMatrixRemapped = rotationMatrix.copyOf()
                when (rotation) {
                    Surface.ROTATION_90 -> SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, rotationMatrixRemapped)
                    Surface.ROTATION_270 -> SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_MINUS_Z, rotationMatrixRemapped)
                }

                val orientation = FloatArray(3)
                SensorManager.getOrientation(rotationMatrixRemapped, orientation)
                return@zip orientation
            }
                    .map { values -> Orientation(values[0], values[1], values[2]) }
                    .doOnUnsubscribe {
                        accelSub.unsubscribe()
                        compassSub.unsubscribe()
                    }
                    .subscribeOn(Schedulers.computation())
        }
    }
}