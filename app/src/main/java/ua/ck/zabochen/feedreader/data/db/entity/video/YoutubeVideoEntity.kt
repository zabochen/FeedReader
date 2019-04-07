package ua.ck.zabochen.feedreader.data.db.entity.video

import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.ck.zabochen.feedreader.internal.TABLE_VIDEO

@Entity(tableName = TABLE_VIDEO)
data class YoutubeVideoEntity(
    @PrimaryKey(autoGenerate = false)
    override val itemId: String,
    override val playlistId: String,
    override val videoId: String,
    override val title: String,
    override val description: String,
    override val coverImageUrl: String
) : VideoEntity