package ua.ck.zabochen.feedreader.provider.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

abstract class PreferenceProvider(context: Context) {

    private val appContext: Context = context.applicationContext

    protected val sharedPreference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}