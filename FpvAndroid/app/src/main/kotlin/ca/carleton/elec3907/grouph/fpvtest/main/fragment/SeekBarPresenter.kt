package ca.carleton.elec3907.grouph.fpvtest.main.fragment

import ca.carleton.elec3907.grouph.fpvtest.main.CommonPresenterImpl

/**
 * Created by francois on 16-03-04.
 */
abstract class SeekBarPresenter : CommonPresenterImpl<SeekBarView>() {
    abstract var rotation: Int
    abstract var throttle: Int
}