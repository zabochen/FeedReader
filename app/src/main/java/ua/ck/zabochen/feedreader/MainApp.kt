package ua.ck.zabochen.feedreader

import android.app.Application
import ua.ck.zabochen.feedreader.di.AppComponent
import ua.ck.zabochen.feedreader.di.DaggerAppComponent
import ua.ck.zabochen.feedreader.di.module.*

class MainApp : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun appComponent() = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        setDagger()
    }

    private fun setDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .databaseModule(DatabaseModule())
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .viewModelFactoryModule(ViewModelFactoryModule())
            .build()
    }
}