package ca.carleton.elec3907.grouph.fpvtest.net

/**
 * Created by francois on 16-03-04.
 */
abstract class RemoteDevice(val addr: String, val port: Int) {
    abstract fun connect()
    abstract fun send(data: ByteArray)
    abstract fun disconnect()
}