package ca.carleton.elec3907.grouph.fpvtest.main

import ca.carleton.elec3907.grouph.fpvtest.R
import ca.carleton.elec3907.grouph.fpvtest.ext.log
import ca.carleton.elec3907.grouph.fpvtest.ext.loge
import ca.carleton.elec3907.grouph.fpvtest.net.UdpRemoteDevice
import ca.carleton.elec3907.grouph.fpvtest.res.Res

/**
 * Created by francois on 16-03-04.
 */
abstract class CommonPresenterImpl<T> : CommonPresenter<T> {
    private val vehicle = UdpRemoteDevice(
            Res.getString(R.string.VEHICLE_IP),
            Res.getInt(R.integer.VEHICLE_PORT)) //TODO inject this

    init {
        log(vehicle.addr)
        log(vehicle.port.toString())
    }

    override fun onStart(view: T) {
        vehicle.connect()
    }

    override fun sendToNetwork(data: ByteArray) {
        vehicle.send(data)
    }

    override fun onError(t: Throwable) {
        loge("Error", t)
    }

    override fun onStop() {
        vehicle.disconnect()
    }
}