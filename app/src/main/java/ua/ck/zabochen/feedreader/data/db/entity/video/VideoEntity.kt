package ua.ck.zabochen.feedreader.data.db.entity.video

interface VideoEntity {
    val itemId: String
    val playlistId: String
    val videoId: String
    val title: String
    val description: String
    val coverImageUrl: String
}