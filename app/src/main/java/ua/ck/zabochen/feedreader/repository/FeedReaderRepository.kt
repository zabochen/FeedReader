package ua.ck.zabochen.feedreader.repository

import androidx.lifecycle.LiveData
import ua.ck.zabochen.feedreader.data.db.entity.video.YoutubeVideoEntity

interface FeedReaderRepository {
    // TODO: Replace YoutubeVideoEntry to VideoEntry =>
    suspend fun getYoutubePlaylistVideo(): LiveData<List<YoutubeVideoEntity>>
}