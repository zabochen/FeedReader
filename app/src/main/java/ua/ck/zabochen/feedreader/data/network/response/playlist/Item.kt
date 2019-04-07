package ua.ck.zabochen.feedreader.data.network.response.playlist

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("snippet")
    val snippet: Snippet
)