package ca.carleton.elec3907.grouph.fpvtest.widget

import android.widget.SeekBar
import java.util.*

/**
 * Created by francois on 16-02-20.
 */
class SeekBarSnapHelper(private val seekBarMultiListeners: SeekBarMultiListeners) : OnSeekBarReleasedListener {
    init {
        seekBarMultiListeners.onReleasedListeners += this
    }

    val snapPositions = LinkedHashSet<Int>()

    override fun onSeekBarReleased(seekBar: SeekBar, progress: Int) {
        val snappedProgress = snapPositions.findNearest(progress)
        seekBar.progress = snappedProgress
    }

    fun addSnapPoint(snapPoint: Int) {
        snapPositions += snapPoint
    }

    fun removeSnapPoint(snapPoint: Int) {
        snapPositions -= snapPoint
    }

    fun unregister() {
        seekBarMultiListeners.onReleasedListeners -= this
    }
}

fun Set<Int>.findNearest(target: Int): Int {
    return minBy { Math.abs(it - target) } ?: target
}