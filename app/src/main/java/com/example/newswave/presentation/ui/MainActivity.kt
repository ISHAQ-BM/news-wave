package com.example.newswave.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newswave.R
import com.example.newswave.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding?.bottomNavigation?.setupWithNavController(navHostFragment.findNavController())


        navHostFragment.findNavController().addOnDestinationChangedListener { _ , destination , _ ->
            when(destination.id){
                R.id.articleFragment -> hideBottomNav()
                else -> showBottomNav()
            }

        }
    }
    private fun showBottomNav() {
        binding?.bottomNavigation?.visibility= View.VISIBLE

    }

    private fun hideBottomNav() {
        binding?.bottomNavigation?.visibility = View.GONE

    }
}