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
    private lateinit var handler: Handler

    override fun connect() {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        handler.post { socket.connect(InetAddress.getByName(addr), port) }
    }

    override fun send(data: ByteArray) {
        if (!handlerThread.isAlive) {
            throw IllegalStateException("connect() must be called before send()")
        }

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
        handlerThread.quitSafely()
    }
}