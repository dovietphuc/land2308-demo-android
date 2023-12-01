package com.example.demodrawerlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var navView: NavigationView
    lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
        val drawerLayout = findViewById<DrawerLayout>(R.id.root)
        navView = findViewById<NavigationView>(R.id.nav_view)

        drawerToggle = ActionBarDrawerToggle(this, drawerLayout,
            R.string.app_name,
            R.string.app_name)

        drawerLayout.setDrawerListener(drawerToggle)

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val aboutFragment = AboutFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.host_container, homeFragment)
            .commit()
        navView.setCheckedItem(R.id.action_home)

        navView.setNavigationItemSelectedListener { menuItem ->
            var fragment: Fragment? = null
            if(menuItem.itemId == R.id.action_home) {
                fragment = homeFragment
            } else if(menuItem.itemId == R.id.action_profile) {
                fragment = profileFragment
            } else if(menuItem.itemId == R.id.action_about) {
                fragment = aboutFragment
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.host_container, it)
                    .commit()
                navView.setCheckedItem(menuItem)
                drawerLayout.closeDrawers()
                return@setNavigationItemSelectedListener true
            }
            return@setNavigationItemSelectedListener false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}