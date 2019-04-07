package ua.ck.zabochen.feedreader.data.network

import androidx.lifecycle.LiveData
import ua.ck.zabochen.feedreader.data.network.response.playlist.YoutubePlaylistVideoResponse

interface FeedReaderNetwork {
    val downloadedYoutubePlaylistVideo: LiveData<YoutubePlaylistVideoResponse>
    suspend fun fetchYoutubeVideoFromPlaylist(playlistId: String)
}