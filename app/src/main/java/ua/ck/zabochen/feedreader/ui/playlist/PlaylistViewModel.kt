package ua.ck.zabochen.feedreader.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ua.ck.zabochen.feedreader.data.db.entity.video.YoutubeVideoEntity
import ua.ck.zabochen.feedreader.internal.lazyDeferred
import ua.ck.zabochen.feedreader.repository.FeedReaderRepository

class PlaylistViewModel(private val feedReaderRepository: FeedReaderRepository) : ViewModel() {

    val youtubePlaylistVideo by lazyDeferred {
        feedReaderRepository.getYoutubePlaylistVideo()
    }

    // If need fetch data after rotate device
    suspend fun getYoutubeVideo(): LiveData<List<YoutubeVideoEntity>> {
        return feedReaderRepository.getYoutubePlaylistVideo()
    }
}