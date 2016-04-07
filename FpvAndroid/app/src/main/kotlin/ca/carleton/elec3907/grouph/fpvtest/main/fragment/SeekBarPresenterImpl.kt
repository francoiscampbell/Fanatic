package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import android.util.Log

/**
 * Created by francois on 16-03-04.
 */
class SeekBarPresenterImpl : SeekBarPresenter() {
    companion object {
        private val rotationMap = arrayOf<Byte>(82, 90, 92)
    }
    private var view: SeekBarView? = null

    override var rotation = 0
        set(value) {
            field = value / 50
            sendRotationAndThrottle()
        }
    override var throttle = 0
        set(value) {
            field = value
            sendRotationAndThrottle()
        }

    override fun onStart(view: SeekBarView) {
        super.onStart(view)
        this.view = view
    }

    private fun sendRotationAndThrottle() {
        val rotationByte = rotationMap[rotation]
        val throttleByte = (throttle * 2.55).toByte()
        sendToNetwork(byteArrayOf(rotationByte, throttleByte))
        Log.i("SeekBarPresenter", "Sent rotation: $rotationByte and throttle: $throttleByte")
    }

    override fun onStop() {
        super.onStop()
        view = null
    }
}