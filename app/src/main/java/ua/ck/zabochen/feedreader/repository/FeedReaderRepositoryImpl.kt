package ua.ck.zabochen.feedreader.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import ua.ck.zabochen.feedreader.data.db.dao.VideoDao
import ua.ck.zabochen.feedreader.data.db.entity.video.YoutubeVideoEntity
import ua.ck.zabochen.feedreader.data.network.FeedReaderNetwork
import ua.ck.zabochen.feedreader.data.network.response.playlist.YoutubePlaylistVideoResponse
import ua.ck.zabochen.feedreader.provider.navigation.NavigationProvider

class FeedReaderRepositoryImpl(
    private val videoDao: VideoDao,
    private val feedReaderNetwork: FeedReaderNetwork,
    private val navigationProvider: NavigationProvider
) : FeedReaderRepository, AnkoLogger {

    init {
        feedReaderNetwork.downloadedYoutubePlaylistVideo.observeForever { youtubePlaylistResponse ->
            info { "=== feedReaderNetwork.downloadedYoutubePlaylistVideo.observeForever ===" }
            persistYoutubePlaylist(youtubePlaylistResponse)
        }
    }

    private fun persistYoutubePlaylist(youtubePlaylistVideo: YoutubePlaylistVideoResponse) {

        // TODO: Save response to Room
        GlobalScope.launch(Dispatchers.IO) {
            // Delete Data
            videoDao.deleteAllVideoByPlaylistId(getCurrentPlaylistId())

            // Insert Data
            for (i in youtubePlaylistVideo.items) {
                val video = YoutubeVideoEntity(
                    itemId = i.id,
                    playlistId = getCurrentPlaylistId(),
                    videoId = i.snippet.resourceId.videoId,
                    title = i.snippet.title,
                    description = i.snippet.description,
                    coverImageUrl = i.snippet.thumbnails.standard.url
                )

                info { i.snippet.title }
                videoDao.insertVideo(video)
            }
        }
    }

    override suspend fun getYoutubePlaylistVideo(): LiveData<List<YoutubeVideoEntity>> {
        return withContext(Dispatchers.IO) {
            feedReaderNetwork.fetchYoutubeVideoFromPlaylist(getCurrentPlaylistId())
            return@withContext videoDao.selectAllVideoByPlaylistId(getCurrentPlaylistId())
        }
    }

    private fun getCurrentPlaylistId(): String {
        return navigationProvider.getNavigationViewItemSelected().playlistId
    }
}