package ca.carleton.elec3907.grouph.fpvtest.widget

import android.widget.SeekBar
import java.util.*

/**
 * Created by francois on 16-02-20.
 */
class SeekBarSnapHelper(private val seekBarMultiListeners: SeekBarMultiListeners) {
    private val onReleased = { seekBar: SeekBar, progress: Int ->
        seekBar.progress = snapPositions.findNearest(progress)
    }

    init {
        seekBarMultiListeners.onReleasedListeners += onReleased
    }

    val snapPositions = LinkedHashSet<Int>()

    fun addSnapPoint(snapPoint: Int) {
        snapPositions += snapPoint
    }
}

fun Set<Int>.findNearest(target: Int): Int {
    return minBy { Math.abs(it - target) } ?: target
}