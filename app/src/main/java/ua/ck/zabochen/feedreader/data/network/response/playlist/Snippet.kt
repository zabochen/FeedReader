package ua.ck.zabochen.feedreader.data.network.response.playlist

import com.google.gson.annotations.SerializedName

data class Snippet(
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("channelId")
    val channelId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails,
    @SerializedName("channelTitle")
    val channelTitle: String,
    @SerializedName("playlistId")
    val playlistId: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("resourceId")
    val resourceId: ResourceId
)