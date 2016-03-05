package ca.carleton.elec3907.grouph.fpvtest

import android.app.Application

/**
 * Created by francois on 16-03-05.
 */
class FpvAndroid : Application() {
    companion object {
        lateinit var INSTANCE: FpvAndroid
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}
