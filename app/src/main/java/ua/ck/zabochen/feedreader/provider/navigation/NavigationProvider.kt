package ua.ck.zabochen.feedreader.provider.navigation

import ua.ck.zabochen.feedreader.internal.enums.Playlist

interface NavigationProvider {
    fun setNavigationViewItemSelected(item: Playlist)
    fun getNavigationViewItemSelected(): Playlist
}