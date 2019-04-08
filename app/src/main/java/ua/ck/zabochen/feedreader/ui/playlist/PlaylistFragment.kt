package ua.ck.zabochen.feedreader.ui.playlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import ua.ck.zabochen.feedreader.MainApp
import ua.ck.zabochen.feedreader.R
import ua.ck.zabochen.feedreader.data.db.entity.video.YoutubeVideoEntity
import ua.ck.zabochen.feedreader.internal.INTENT_YOUTUBE_VIDEO_DESCRIPTION_KEY
import ua.ck.zabochen.feedreader.internal.INTENT_YOUTUBE_VIDEO_ID_KEY
import ua.ck.zabochen.feedreader.internal.listener.RecyclerViewItemTouchListener
import ua.ck.zabochen.feedreader.ui.base.BaseFragment
import ua.ck.zabochen.feedreader.ui.player.YoutubePlayerActivity
import javax.inject.Inject

class PlaylistFragment : BaseFragment(), AnkoLogger {

    companion object {
        fun newInstance() = PlaylistFragment()
    }

    // ViewModel & Factory
    private lateinit var playlistViewModel: PlaylistViewModel

    @Inject
    lateinit var playlistViewModelFactory: PlaylistViewModelFactory

    // Adapter
    private val playlistAdapter: PlaylistAdapter by lazy { PlaylistAdapter() }

    // Views
    private lateinit var unbinder: Unbinder

    @BindView(R.id.fragmentPlaylist_group_loading)
    lateinit var playlistLoadingGroup: Group

    @BindView(R.id.fragmentPlaylist_recyclerView_playlist)
    lateinit var playlistRecyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Dagger
        MainApp.appComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_playlist, container, false).also {
            this.unbinder = ButterKnife.bind(this, it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Start loading
        showLoading()

        // Attach ViewModel
        // "this" - recreate fragment & fetch new data
        // "activity" - recreate fragment & fetch old data
        this.playlistViewModel = ViewModelProviders.of(this, playlistViewModelFactory)
            .get(PlaylistViewModel::class.java)

        launch {
            val youtubePlaylistVideo = playlistViewModel.youtubePlaylistVideo.await()
            youtubePlaylistVideo.observe(this@PlaylistFragment, Observer { playlistVideo ->
                if (playlistVideo == null) return@Observer
                setUi(playlistVideo)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::unbinder.isInitialized) {
            unbinder.unbind()
        }
    }

    private fun setUi(videoList: List<YoutubeVideoEntity>) {
        // Adapter
        this.playlistAdapter.setData(ArrayList(videoList))

        // RecyclerView
        this.playlistRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = playlistAdapter
            addOnItemTouchListener(RecyclerViewItemTouchListener(
                context = activity!!.applicationContext,
                recyclerView = this,
                clickListener = object : RecyclerViewItemTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        // Start YoutubePlayerActivity
                        startActivity(Intent(activity, YoutubePlayerActivity::class.java).apply {
                            // Add Video Id
                            putExtra(
                                INTENT_YOUTUBE_VIDEO_ID_KEY,
                                playlistAdapter.getData()[position].videoId
                            )
                            // Add Video Description
                            putExtra(
                                INTENT_YOUTUBE_VIDEO_DESCRIPTION_KEY,
                                playlistAdapter.getData()[position].description
                            )
                        })
                    }

                    override fun onLongClick(view: View, position: Int) {
                    }
                }
            ))
        }

        // Show data
        hideLoading()
    }

    private fun showLoading() {
        this.playlistRecyclerView.visibility = View.GONE
        this.playlistLoadingGroup.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        this.playlistRecyclerView.visibility = View.VISIBLE
        this.playlistLoadingGroup.visibility = View.GONE
    }
}