package ca.carleton.elec3907.grouph.fpvtest.abs

/**
 * Created by francois on 16-03-04.
 */
interface Presenter <in T> {
    fun onStart(view: T)
    fun onStop()
}