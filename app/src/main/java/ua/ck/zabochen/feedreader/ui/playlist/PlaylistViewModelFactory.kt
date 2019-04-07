package ua.ck.zabochen.feedreader.ui.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.ck.zabochen.feedreader.repository.FeedReaderRepository

class PlaylistViewModelFactory(private val feedReaderRepository: FeedReaderRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaylistViewModel(feedReaderRepository) as T
    }
}