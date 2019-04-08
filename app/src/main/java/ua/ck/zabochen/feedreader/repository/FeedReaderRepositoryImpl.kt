package ua.ck.zabochen.feedreader.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.ck.zabochen.feedreader.data.db.dao.VideoDao
import ua.ck.zabochen.feedreader.data.db.entity.video.YoutubeVideoEntity
import ua.ck.zabochen.feedreader.data.network.FeedReaderNetwork
import ua.ck.zabochen.feedreader.data.network.response.playlist.YoutubePlaylistVideoResponse
import ua.ck.zabochen.feedreader.provider.navigation.NavigationProvider

class FeedReaderRepositoryImpl(
    private val videoDao: VideoDao,
    private val feedReaderNetwork: FeedReaderNetwork,
    private val navigationProvider: NavigationProvider
) : FeedReaderRepository {

    init {
        feedReaderNetwork.downloadedYoutubePlaylistVideo.observeForever { youtubePlaylistResponse ->
            persistYoutubePlaylist(youtubePlaylistResponse)
        }
    }

    private fun persistYoutubePlaylist(youtubePlaylistVideo: YoutubePlaylistVideoResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            // Delete data in database
            videoDao.deleteAllVideoByPlaylistId(getCurrentPlaylistId())

            // Fill videoList
            val videoList: MutableList<YoutubeVideoEntity> = arrayListOf()
            youtubePlaylistVideo.items.forEach { item ->
                videoList.add(
                    YoutubeVideoEntity(
                        itemId = item.id,
                        playlistId = getCurrentPlaylistId(),
                        videoId = item.snippet.resourceId.videoId,
                        title = item.snippet.title,
                        description = item.snippet.description,
                        coverImageUrl = item.snippet.thumbnails.standard.url
                    )
                )
            }

            // Insert data into database
            videoDao.insertVideo(videoList)
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