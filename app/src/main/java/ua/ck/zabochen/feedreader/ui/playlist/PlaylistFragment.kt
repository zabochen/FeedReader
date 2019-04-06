package ua.ck.zabochen.feedreader.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.ck.zabochen.feedreader.R
import ua.ck.zabochen.feedreader.ui.base.BaseFragment

class PlaylistFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }
}