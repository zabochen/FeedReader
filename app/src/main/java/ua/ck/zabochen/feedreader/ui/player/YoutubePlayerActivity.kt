package ua.ck.zabochen.feedreader.ui.player

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import ua.ck.zabochen.feedreader.R
import ua.ck.zabochen.feedreader.internal.INTENT_YOUTUBE_VIDEO_DESCRIPTION_KEY
import ua.ck.zabochen.feedreader.internal.INTENT_YOUTUBE_VIDEO_ID_KEY
import ua.ck.zabochen.feedreader.internal.YOUTUBE_API_KEY

class YoutubePlayerActivity : YouTubeBaseActivity(),
    YouTubePlayer.OnInitializedListener,
    YouTubePlayer.OnFullscreenListener {

    private val portraitOrientation: Int = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
    private val landscapeOrientation: Int = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

    private var yPlayer: YouTubePlayer? = null
    private var autoOrientation: Boolean = false

    private lateinit var unbinder: Unbinder

    @BindView(R.id.activityYoutubePlayer_youtubePlayer)
    lateinit var youtubePlayerView: YouTubePlayerView

    @BindView(R.id.activityYoutubePlayer_textView_videoDescription)
    lateinit var videoDescription: TextView

    override fun onCreate(bundel: Bundle?) {
        super.onCreate(bundel)
        this.autoOrientation = Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0) == 1
        setUi()
        setYoutubePlayer()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        if (newConfig!!.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (yPlayer != null) {
                yPlayer!!.setFullscreen(true)
            }
        }

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (yPlayer != null) {
                yPlayer!!.setFullscreen(false)
            }
        }
    }

    override fun onFullscreen(fullSize: Boolean) {
        if (fullSize) {
            requestedOrientation = landscapeOrientation
        } else {
            requestedOrientation = portraitOrientation
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::unbinder.isInitialized) {
            this.unbinder.unbind()
        }
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        this.yPlayer = player
        this.yPlayer!!.setOnFullscreenListener(this)

        if (autoOrientation) {
            this.yPlayer!!.addFullscreenControlFlag(
                YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                        or YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                        or YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
                        or YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT
            )
        } else {
            this.yPlayer!!.addFullscreenControlFlag(
                YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                        or YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                        or YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT
            )
        }

        // Play Video
        if (!wasRestored) {
            yPlayer!!.loadVideo(getVideoId())
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, player: YouTubeInitializationResult?) {
    }

    private fun setUi() {
        // Layout & ButterKnife
        setContentView(R.layout.activity_youtube_player)
        this.unbinder = ButterKnife.bind(this)

        // Video Description
        this.videoDescription.text = getVideoDescription()
    }

    private fun setYoutubePlayer() {
        this.youtubePlayerView.initialize(YOUTUBE_API_KEY, this)
    }

    private fun getVideoId(): String {
        return intent.getStringExtra(INTENT_YOUTUBE_VIDEO_ID_KEY)
    }

    private fun getVideoDescription(): String {
        return intent.getStringExtra(INTENT_YOUTUBE_VIDEO_DESCRIPTION_KEY)
    }
}