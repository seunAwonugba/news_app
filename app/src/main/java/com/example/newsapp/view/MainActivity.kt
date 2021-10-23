package com.example.newsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.db.NewsAppDao
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var bottomNav : BottomNavigationView
    private lateinit var navHostFragment : FragmentContainerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNav = findViewById(R.id.bottomNavigationView)
        navHostFragment = findViewById(R.id.fragmentContainerView)


        // set up bottom nav with nav controller
//        bottomNav.setupWithNavController(navHostFragment)
//        binding.bottomNavigationView.setupWithNavController(binding.fragmentContainerView.findNavController())

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNav.setupWithNavController(navController)

    }
}