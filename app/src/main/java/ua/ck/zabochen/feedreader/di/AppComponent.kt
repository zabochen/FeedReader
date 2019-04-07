package ua.ck.zabochen.feedreader.di

import dagger.Component
import ua.ck.zabochen.feedreader.di.module.*
import ua.ck.zabochen.feedreader.ui.main.MainActivity
import ua.ck.zabochen.feedreader.ui.playlist.PlaylistFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(playlistFragment: PlaylistFragment)
}