package ua.ck.zabochen.feedreader.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ua.ck.zabochen.feedreader.data.network.FeedReaderNetwork
import ua.ck.zabochen.feedreader.data.network.FeedReaderNetworkImpl
import ua.ck.zabochen.feedreader.data.network.interceptor.connection.ConnectionStateInterceptor
import ua.ck.zabochen.feedreader.data.network.interceptor.connection.ConnectionStateInterceptorImpl
import ua.ck.zabochen.feedreader.data.network.service.YoutubeApiService
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class NetworkModule {

    @Singleton
    @Provides
    fun provideConnectionStateInterceptor(context: Context): ConnectionStateInterceptor {
        return ConnectionStateInterceptorImpl(context)
    }

    @Singleton
    @Provides
    fun provideYoutubeApiService(connectionStateInterceptor: ConnectionStateInterceptor): YoutubeApiService {
        return YoutubeApiService(connectionStateInterceptor)
    }

    @Singleton
    @Provides
    fun provideFeedReaderNetwork(youtubeApiService: YoutubeApiService): FeedReaderNetwork {
        return FeedReaderNetworkImpl(youtubeApiService)
    }
}