package ca.carleton.elec3907.grouph.fpvtest.main

import ca.carleton.elec3907.grouph.fpvtest.abs.Presenter

/**
 * Created by francois on 16-03-04.
 */
interface CommonPresenter<T> : Presenter<T> {
    fun sendToNetwork(data: ByteArray)
    fun onError(t: Throwable)
}