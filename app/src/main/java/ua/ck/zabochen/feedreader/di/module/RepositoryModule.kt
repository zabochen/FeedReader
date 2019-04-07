package ua.ck.zabochen.feedreader.di.module

import dagger.Module
import dagger.Provides
import ua.ck.zabochen.feedreader.data.db.dao.VideoDao
import ua.ck.zabochen.feedreader.data.network.FeedReaderNetwork
import ua.ck.zabochen.feedreader.provider.navigation.NavigationProvider
import ua.ck.zabochen.feedreader.repository.FeedReaderRepository
import ua.ck.zabochen.feedreader.repository.FeedReaderRepositoryImpl
import javax.inject.Singleton

@Module(includes = [AppModule::class, DatabaseModule::class, NetworkModule::class])
class RepositoryModule {

    @Singleton
    @Provides
    fun provideFeedReaderRepository(
        videoDao: VideoDao,
        feedReaderNetwork: FeedReaderNetwork,
        navigationProvider: NavigationProvider
    ): FeedReaderRepository {
        return FeedReaderRepositoryImpl(videoDao, feedReaderNetwork, navigationProvider)
    }
}