package ca.carleton.elec3907.grouph.fpvtest.net

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException

/**
 * Created by francois on 16-03-04.
 */
class UdpRemoteDevice(addr: String, port: Int) : RemoteDevice(addr, port) {
    private var socket = DatagramSocket()

    override fun connect() {
        socket.connect(InetAddress.getByName(addr), port)
    }

    override fun send(data: ByteArray) {
        checkConnected()
        socket.send(DatagramPacket(data, data.size))
    }

    override fun disconnect() {
        socket.disconnect()
    }

    fun checkConnected() {
        if (!socket.isConnected) {
            throw SocketException("Socket not connected")
        }
    }
}