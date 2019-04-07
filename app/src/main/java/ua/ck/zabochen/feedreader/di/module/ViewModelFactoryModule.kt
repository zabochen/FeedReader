package ua.ck.zabochen.feedreader.di.module

import dagger.Module
import dagger.Provides
import ua.ck.zabochen.feedreader.repository.FeedReaderRepository
import ua.ck.zabochen.feedreader.ui.playlist.PlaylistViewModelFactory

@Module(includes = [AppModule::class, RepositoryModule::class])
class ViewModelFactoryModule {

    @Provides
    fun providePlaylistViewModelFactory(feedReaderRepository: FeedReaderRepository): PlaylistViewModelFactory {
        return PlaylistViewModelFactory(feedReaderRepository)
    }
}