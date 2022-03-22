package com.utkarsh.spokesly.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utkarsh.spokesly.R
import com.utkarsh.spokesly.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //Navigation Host Fragment.
        navController = findNavController(R.id.nav_host_fragment)

        //Bottom Navigation Implementation.
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNavigationView.setupWithNavController(navController)


        val bottomNav = findViewById(R.id.bottom_navigation_menu) as BottomNavigationView
        bottomNav.setupWithNavController(navController)

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {

                R.id.homeFragment -> {
                    navigateToFragment(R.id.homeFragment)
                }
                R.id.postsFragment -> {
                    navigateToFragment(R.id.postsFragment)
                }
                R.id.contactsFragment -> {
                    navigateToFragment(R.id.contactsFragment)
                }
                R.id.usersFragment -> {
                    navigateToFragment(R.id.usersFragment)
                }
                R.id.postImageFragment -> {
                    navigateToFragment(R.id.postImageFragment)
                }
            }
            false
        }
    }

    private fun navigateToFragment(argFragmentId: Int) {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.popBackStack()
        navController.navigate(argFragmentId)
    }
}