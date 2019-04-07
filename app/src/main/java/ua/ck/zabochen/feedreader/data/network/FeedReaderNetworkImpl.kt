package ua.ck.zabochen.feedreader.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import ua.ck.zabochen.feedreader.data.network.response.playlist.YoutubePlaylistVideoResponse
import ua.ck.zabochen.feedreader.data.network.service.YoutubeApiService
import ua.ck.zabochen.feedreader.internal.NoConnectionException

class FeedReaderNetworkImpl(private val youtubeApiService: YoutubeApiService) : FeedReaderNetwork, AnkoLogger {

    private val _downloadedYouTubePlaylist = MutableLiveData<YoutubePlaylistVideoResponse>()
    override val downloadedYoutubePlaylistVideo: LiveData<YoutubePlaylistVideoResponse>
        get() = _downloadedYouTubePlaylist

    override suspend fun fetchYoutubeVideoFromPlaylist(playlistId: String) {
        try {
            val fetchYoutubePlaylist = youtubeApiService.getYoutubePlaylistVideoAsync(playlistId = playlistId).await()
            _downloadedYouTubePlaylist.postValue(fetchYoutubePlaylist)
        } catch (e: NoConnectionException) {
            info { "NoConnectionException: No Internet Connection" }
        }
    }
}