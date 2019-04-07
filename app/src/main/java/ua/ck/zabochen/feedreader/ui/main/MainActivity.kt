package ua.ck.zabochen.feedreader.ui.main

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.google.android.material.navigation.NavigationView
import org.jetbrains.anko.AnkoLogger
import ua.ck.zabochen.feedreader.MainApp
import ua.ck.zabochen.feedreader.R
import ua.ck.zabochen.feedreader.internal.enums.Playlist
import ua.ck.zabochen.feedreader.provider.navigation.NavigationProvider
import ua.ck.zabochen.feedreader.ui.playlist.PlaylistFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), AnkoLogger {

    @Inject
    lateinit var navigationProvider: NavigationProvider

    private lateinit var unbinder: Unbinder

    @BindView(R.id.activityMain_drawerLayout)
    lateinit var drawerLayout: DrawerLayout

    @BindView(R.id.activityMain_toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.activityMain_navigationView)
    lateinit var navigationView: NavigationView

    @BindView(R.id.activityMain_frameLayout_fragmentHolder)
    lateinit var fragmentHolder: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApp.appComponent().inject(this)
        setUi()
        if (savedInstanceState == null) setFragment(fragmentHolder, PlaylistFragment.newInstance())
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::unbinder.isInitialized) {
            this.unbinder.unbind()
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setUi() {
        // Layout & ButterKnife
        setContentView(R.layout.activity_main)
        this.unbinder = ButterKnife.bind(this)

        // Toolbar
        setSupportActionBar(toolbar)

        // DrawerLayout & Toogle
        val drawerToogle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawerLayout_open, R.string.drawerLayout_close
        )
        this.drawerLayout.addDrawerListener(drawerToogle)
        drawerToogle.syncState()

        // NavigationView
        setSelectedNavigationViewItem()
        this.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuNavigationView_item_androidJetpack -> {
                    navigationProvider.setNavigationViewItemSelected(Playlist.ANDROID_JETPACK)
                    updateFragment()
                }
                R.id.menuNavigationView_item_androidKotlin -> {
                    navigationProvider.setNavigationViewItemSelected(Playlist.ANDROID_KOTLIN)
                    updateFragment()
                }
                R.id.menuNavigationView_item_androidThings -> {
                    navigationProvider.setNavigationViewItemSelected(Playlist.ANDROID_THINGS)
                    updateFragment()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }

    private fun setSelectedNavigationViewItem() {
        when (navigationProvider.getNavigationViewItemSelected()) {
            Playlist.ANDROID_JETPACK -> checkedNavigationViewItem(0)
            Playlist.ANDROID_KOTLIN -> checkedNavigationViewItem(1)
            Playlist.ANDROID_THINGS -> checkedNavigationViewItem(2)
        }
    }

    private fun checkedNavigationViewItem(itemIndex: Int) {
        navigationView.menu.getItem(itemIndex).isChecked = true
    }

    private fun setFragment(fragmentHolder: FrameLayout, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentHolder.id, fragment)
            .commit()
    }

    private fun updateFragment() {
        setFragment(fragmentHolder, PlaylistFragment.newInstance())
    }
}
