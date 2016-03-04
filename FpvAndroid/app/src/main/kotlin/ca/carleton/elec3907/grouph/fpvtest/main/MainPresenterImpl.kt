package ca.carleton.elec3907.grouph.fpvtest.main

import ca.carleton.elec3907.grouph.fpvtest.net.UdpRemoteDevice

/**
 * Created by francois on 16-03-04.
 */
class MainPresenterImpl : MainPresenter() {
    private val vehicle = UdpRemoteDevice("192.168.4.1", 3907)

    override fun onStart(view: MainView) {
        super.onStart(view)
        vehicle.connect()
    }

    override fun sendToNetwork(data: ByteArray) {
        vehicle.send(data)
    }

    override fun onStop() {
        vehicle.disconnect()
    }
}