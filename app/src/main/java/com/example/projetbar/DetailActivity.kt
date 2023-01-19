package com.example.projetbar

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.projetbar.databinding.ActivityDetailBinding
import com.example.projetbar.ui.dashboard.DashboardFragment
import com.example.projetbar.ui.detail.DetailFragement
import com.example.projetbar.ui.home.HomeFragment
import com.example.projetbar.ui.home.HomeViewModel
import com.example.projetbar.ui.map.MapsFragment

class DetailActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: HomeViewModel
    lateinit var currentFragment: String
    private lateinit var mainActivity: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_detail)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)



    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_detail)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun goMap(){

        viewModel.inMap = true

        currentFragment = "map"

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_content_detail, MapsFragment())
        fragmentTransaction.commit()
    }

    fun goBackHome () {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(DetailFragement())
        fragmentTransaction.commit()

        viewModel.inDetail = false
        currentFragment = "home"
    }

    fun goBackDetail () {

        viewModel.inMap = false

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(MapsFragment())
        fragmentTransaction.commit()

        viewModel.inDetail = true
        currentFragment = "detail"
    }

    override fun onBackPressed() {
        if (currentFragment == "detail") {
            goBackHome()
        } else if (currentFragment == "map") {
            goBackDetail()
        }
    }
}