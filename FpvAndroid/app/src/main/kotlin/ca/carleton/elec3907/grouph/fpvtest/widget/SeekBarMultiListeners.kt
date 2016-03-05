package ca.carleton.elec3907.grouph.fpvtest.widget

import android.widget.SeekBar
import java.util.*

/**
 * Created by francois on 16-02-20.
 */
class SeekBarMultiListeners(private val seekBar: SeekBar) : SeekBar.OnSeekBarChangeListener {
    val onProgressChangedListeners = HashSet<OnSeekBarProgressChangeListener>()
    val onReleasdListeners = HashSet<OnSeekBarReleasedListener>()

    init {
        seekBar.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
        onProgressChangedListeners.forEach { it.onProgressChanged(p0, p1, p2) }
    }

    override fun onStartTrackingTouch(p0: SeekBar) {
    }

    override fun onStopTrackingTouch(p0: SeekBar) {
        onReleasdListeners.forEach { it.onSeekBarReleased(p0, p0.progress) }
    }

    fun unregister() {
        seekBar.setOnSeekBarChangeListener(null)
    }
}

interface OnSeekBarProgressChangeListener {
    fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean)
}

interface OnSeekBarReleasedListener {
    fun onSeekBarReleased(seekBar: SeekBar, progress: Int)
}


