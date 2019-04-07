package ua.ck.zabochen.feedreader.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ua.ck.zabochen.feedreader.data.db.dao.VideoDao
import ua.ck.zabochen.feedreader.data.db.entity.video.YoutubeVideoEntity
import ua.ck.zabochen.feedreader.internal.DATABASE_NAME
import ua.ck.zabochen.feedreader.internal.DATABASE_VERSION

@Database(
    entities = [YoutubeVideoEntity::class],
    version = DATABASE_VERSION
)
abstract class FeedReaderDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao

    companion object {

        @Volatile
        private var databaseInstance: FeedReaderDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context): FeedReaderDatabase {
            return databaseInstance ?: synchronized<FeedReaderDatabase>(lock) {
                return databaseInstance ?: buildFeedReaderDatabase(context).also { databaseInstance = it }
            }
        }

        private fun buildFeedReaderDatabase(context: Context): FeedReaderDatabase {
            return Room.databaseBuilder(context.applicationContext, FeedReaderDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}