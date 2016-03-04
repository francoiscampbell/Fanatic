package ca.carleton.elec3907.grouph.fpvtest.main

import ca.carleton.elec3907.grouph.fpvtest.abs.Presenter

/**
 * Created by francois on 16-03-04.
 */
abstract class MainPresenter : Presenter<MainView>() {
    protected lateinit var view: MainView

    override fun onStart(view: MainView) {
        this.view = view
    }

    abstract fun sendToNetwork(data: ByteArray)

    abstract fun onStop()
}