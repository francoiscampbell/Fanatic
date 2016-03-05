package ca.carleton.elec3907.grouph.fpvtest.res

import ca.carleton.elec3907.grouph.fpvtest.FpvAndroid
import android.content.res.Resources as AndroidRes

/**
 * Created by francois on 16-03-05.
 */
class Res {
    companion object {
        private val resources by lazy { FpvAndroid.INSTANCE.resources } //TODO inject this

        fun getInt(id: Int) = resources.getInteger(id)
        fun getString(id: Int) = resources.getString(id)
    }
}