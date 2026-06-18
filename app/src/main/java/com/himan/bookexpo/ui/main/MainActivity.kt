package com.himan.bookexpo.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.himan.bookexpo.R
import com.himan.bookexpo.databinding.ActivityMainBinding
import com.himan.bookexpo.extensions.getThemeColor
import com.himan.bookexpo.ui.details.BookDetailsFragment
import com.himan.bookexpo.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInsets(binding.linearLayout)
        setupInsets(binding.navigationView)
        setUpToolbar()
        setupDrawerToggle()
        setupDrawerNavigation()

        // Avoid adding the fragment again when the Activity is recreated
        if (savedInstanceState == null) {
            // Set HomeFragment as the default screen
            openHome()
            binding.navigationView.setCheckedItem(R.id.home)
        }

        syncToolbarAndDrawerWithBackStack()
    }

    private fun setupInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpToolbar() {
        // Setting the toolbar as action bar is not required
        // for ActionBarDrawerToggle with toolbar as argument
        //setSupportActionBar(binding.toolbar)

        binding.toolbar.title = "Book Expo"
    }

    private fun setupDrawerToggle() {
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    private fun setupDrawerNavigation() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            val isCurrentDestination =
                binding.navigationView.checkedItem?.itemId == menuItem.itemId

            if (isCurrentDestination) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                return@setNavigationItemSelectedListener true
            }

            when (menuItem.itemId) {
                R.id.home -> openHome()

                else -> return@setNavigationItemSelectedListener false
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun openHome() {
        supportFragmentManager.commit {
            replace<HomeFragment>(R.id.fragmentContainer)
        }
    }

    private fun syncToolbarAndDrawerWithBackStack() {
        supportFragmentManager
            .addOnBackStackChangedListener {

                val currentFragment =
                    supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                        ?: return@addOnBackStackChangedListener

                when (currentFragment) {
                    is BookDetailsFragment ->
                        configureSecondaryToolbarAndDrawer(R.string.title_book_details)

                    is HomeFragment ->
                        configureTopLevelToolbarAndDrawer(R.string.title_home)
                }
            }
    }

    private fun configureSecondaryToolbarAndDrawer(@StringRes toolbarTitle: Int) {

        binding.toolbar.apply {
            title = getString(toolbarTitle)

            setBackgroundColor(
                getThemeColor(
                    com.google.android.material.R.attr.colorSurfaceContainer
                )
            )

            setNavigationIcon(
                R.drawable.ic_arrow_back
            )

            setNavigationOnClickListener {
                //supportFragmentManager.popBackStack()
                onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.drawerLayout.setDrawerLockMode(
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )
    }

    private fun configureTopLevelToolbarAndDrawer(@StringRes toolbarTitle: Int) {

        binding.toolbar.apply {
            title = getString(toolbarTitle)

            setBackgroundColor(
                getThemeColor(
                    com.google.android.material.R.attr.colorSurface
                )
            )

            //navigationIcon = actionBarDrawerToggle.drawerArrowDrawable
            actionBarDrawerToggle.syncState()

            setNavigationOnClickListener {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.drawerLayout.setDrawerLockMode(
            DrawerLayout.LOCK_MODE_UNLOCKED
        )
    }
}