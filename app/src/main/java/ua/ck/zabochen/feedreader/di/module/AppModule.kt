package ua.ck.zabochen.feedreader.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ua.ck.zabochen.feedreader.provider.navigation.NavigationProvider
import ua.ck.zabochen.feedreader.provider.navigation.NavigationProviderImpl
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return context.applicationContext
    }

    @Singleton
    @Provides
    fun provideNavigationProvider(context: Context): NavigationProvider {
        return NavigationProviderImpl(context)
    }
}