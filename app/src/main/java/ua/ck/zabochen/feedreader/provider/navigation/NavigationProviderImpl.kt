package ua.ck.zabochen.feedreader.provider.navigation

import android.content.Context
import ua.ck.zabochen.feedreader.internal.enums.Playlist
import ua.ck.zabochen.feedreader.internal.SHARED_PREFERENCES_NAVIGATION_VIEW_ITEM_SELECTED_KEY
import ua.ck.zabochen.feedreader.provider.preference.PreferenceProvider

class NavigationProviderImpl(context: Context) : PreferenceProvider(context), NavigationProvider {

    override fun setNavigationViewItemSelected(item: Playlist) {
        sharedPreference.edit().putString(
            SHARED_PREFERENCES_NAVIGATION_VIEW_ITEM_SELECTED_KEY,
            item.name
        ).apply()
    }

    override fun getNavigationViewItemSelected(): Playlist {
        val playlistId: String? = sharedPreference.getString(
            SHARED_PREFERENCES_NAVIGATION_VIEW_ITEM_SELECTED_KEY,
            Playlist.ANDROID_JETPACK.name
        )
        return Playlist.valueOf(playlistId!!)
    }
}