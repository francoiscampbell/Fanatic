package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers
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
                    sendToNetwork(byteArrayOf((view!!.rotation * 1.8).toByte(), (view!!.throttle * 2.55).toByte()))
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