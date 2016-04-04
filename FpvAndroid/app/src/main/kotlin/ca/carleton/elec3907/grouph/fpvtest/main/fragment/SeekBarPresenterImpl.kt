package ca.carleton.elec3907.grouph.fpvtest.main.fragment

/**
 * Created by francois on 16-03-04.
 */
class SeekBarPresenterImpl : SeekBarPresenter() {
    private var view: SeekBarView? = null

    override var rotation = 0
        set(value) {
            field = value
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

    private fun sendRotationAndThrottle() = sendToNetwork(byteArrayOf((rotation * 1.8).toByte(), (throttle * 2.55).toByte()))

    override fun onStop() {
        super.onStop()
        view = null
    }
}