package ua.ck.zabochen.feedreader.ui.main

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.google.android.material.navigation.NavigationView
import ua.ck.zabochen.feedreader.R

class MainActivity : AppCompatActivity() {

    private lateinit var unbinder: Unbinder

    @BindView(R.id.activityMain_drawerLayout)
    lateinit var drawerLayout: DrawerLayout

    @BindView(R.id.activityMain_toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.activityMain_navigationView)
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUi()
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
        this.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuNavigationView_item_androidJetpack -> {
                    // TODO: Selected item => menuNavigationView_item_androidJetpack
                }
                R.id.menuNavigationView_item_androidKotlin -> {
                    // TODO: Selected item => menuNavigationView_item_androidKotlin
                }
                R.id.menuNavigationView_item_androidThings -> {
                    // TODO: Selected item => menuNavigationView_item_androidThings
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }
}
