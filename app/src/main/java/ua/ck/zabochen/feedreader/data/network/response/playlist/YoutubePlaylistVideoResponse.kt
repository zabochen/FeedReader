package ua.ck.zabochen.feedreader.data.network.response.playlist

import com.google.gson.annotations.SerializedName

data class YoutubePlaylistVideoResponse(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo,
    @SerializedName("items")
    val items: List<Item>
)