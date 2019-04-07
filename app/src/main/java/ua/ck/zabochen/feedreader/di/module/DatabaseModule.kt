package ua.ck.zabochen.feedreader.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ua.ck.zabochen.feedreader.data.db.FeedReaderDatabase
import ua.ck.zabochen.feedreader.data.db.dao.VideoDao
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFeedReaderDatabase(context: Context): FeedReaderDatabase {
        return FeedReaderDatabase(context)
    }

    @Singleton
    @Provides
    fun provideVideoDao(feedReaderDatabase: FeedReaderDatabase): VideoDao {
        return feedReaderDatabase.videoDao()
    }
}