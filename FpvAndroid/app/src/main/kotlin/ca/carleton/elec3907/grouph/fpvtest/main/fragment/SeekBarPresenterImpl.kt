package ca.carleton.elec3907.grouph.fpvtest.main.fragment

/**
 * Created by francois on 16-03-04.
 */
class SeekBarPresenterImpl : SeekBarPresenter {
    private lateinit var view: SeekBarView

    override fun onStart(view: SeekBarView) {
        this.view = view
    }
}