package ca.carleton.elec3907.grouph.fpvtest.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import ca.carleton.elec3907.grouph.fpvtest.R
import ca.carleton.elec3907.grouph.fpvtest.fragment.OrientationFragment
import ca.carleton.elec3907.grouph.fpvtest.fragment.SeekBarFragment
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    companion object {
        private val TAG = "MainActivity"
    }

    private val btnUseOrientation by lazy { findViewById(R.id.btnUseOrientation) as Button }
    private val btnUseSeekBar by lazy { findViewById(R.id.btnUseSeekBar) as Button }

    private lateinit var socket: DatagramSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        socket = DatagramSocket()

        btnUseOrientation.setOnClickListener {
            supportFragmentManager!!
                    .beginTransaction()
                    .replace(R.id.container, OrientationFragment.newInstance(Bundle.EMPTY))
                    .commit()
        }

        btnUseSeekBar.setOnClickListener {
            supportFragmentManager!!
                    .beginTransaction()
                    .replace(R.id.container, SeekBarFragment.newInstance(Bundle.EMPTY))
                    .commit()
        }
    }

    override fun sendToNetwork(data: ByteArray) {
        if (!socket.isConnected) {
            socket.connect(InetAddress.getByName("192.168.4.1"), 3907)
        }
        socket.send(DatagramPacket(data, data.size))
    }

    override fun onError(t: Throwable) {
        Log.e(TAG, "Error", t)
        socket.disconnect()
    }

    override fun onStop() {
        super.onStop()
        Thread {
            socket.disconnect()
        }.start()
    }

}

interface OnFragmentInteractionListener {
    fun sendToNetwork(data: ByteArray)
    fun onError(t: Throwable)
}
