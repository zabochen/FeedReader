package ua.ck.zabochen.feedreader.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.ck.zabochen.feedreader.data.db.entity.video.YoutubeVideoEntity
import ua.ck.zabochen.feedreader.internal.TABLE_VIDEO

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(videoEntity: YoutubeVideoEntity)

    @Query("DELETE FROM $TABLE_VIDEO WHERE playlistId = :playlistId")
    fun deleteAllVideoByPlaylistId(playlistId: String)

    @Query("SELECT * FROM $TABLE_VIDEO WHERE playlistId = :playlistId")
    fun selectAllVideoByPlaylistId(playlistId: String): LiveData<List<YoutubeVideoEntity>>
}