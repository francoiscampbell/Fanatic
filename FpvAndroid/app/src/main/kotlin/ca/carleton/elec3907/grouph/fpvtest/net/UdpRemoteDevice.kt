package ca.carleton.elec3907.grouph.fpvtest.net

import android.os.Handler
import android.os.HandlerThread
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * Created by francois on 16-03-04.
 */
class UdpRemoteDevice(addr: String, port: Int) : RemoteDevice(addr, port) {
    private val socket = DatagramSocket()

    private val handlerThread = HandlerThread("UDP send loop")
    private val handler: Handler

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }

    override fun connect() {
        handler.post { socket.connect(InetAddress.getByName(addr), port) }
    }

    override fun send(data: ByteArray) {
        handler.post {
            try {
                socket.send(DatagramPacket(data, data.size))
            } catch(e: IOException) {
                if (!(e.message?.contains("ECONNREFUSED") ?: false)) {
                    throw e
                }
            }
        }
    }

    override fun disconnect() {
        handler.post { socket.disconnect() }
    }
}