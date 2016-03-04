package ca.carleton.elec3907.grouph.fpvtest.abs

/**
 * Created by francois on 16-03-04.
 */
abstract class Presenter <in T> {
    abstract fun onStart(view: T)
}