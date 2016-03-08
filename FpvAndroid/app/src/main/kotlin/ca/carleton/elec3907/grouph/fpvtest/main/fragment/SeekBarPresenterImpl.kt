package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.TimeUnit

/**
 * Created by francois on 16-03-04.
 */
class SeekBarPresenterImpl : SeekBarPresenter() {
    private var view: SeekBarView? = null

    override fun onStart(view: SeekBarView) {
        super.onStart(view)
        this.view = view
    }

    private lateinit var networkSub: Subscription

    override fun startSeekBarListener() {
        networkSub = Observable.interval(100L, TimeUnit.MILLISECONDS, Schedulers.io())
                .subscribe ({
                    val buffer = ByteBuffer.allocate(11)
                            .order(ByteOrder.LITTLE_ENDIAN)
                            .put('R'.toByte()) //rotation
                            .put((if (view!!.rotation <= 50) 'L' else 'R').toByte())
                            .putInt(Math.abs(view!!.rotation - 50))
                            .put('T'.toByte()) //throttle
                            .putInt(view!!.throttle)
                    sendToNetwork(buffer.array())
                }, { throwable ->
                    onError(throwable)
                })
    }

    override fun onStop() {
        super.onStop()
        view = null
        networkSub.unsubscribe()
    }
}