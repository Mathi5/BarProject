package com.example.projetbar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
    //private lateinit var mainActivity: MainActivity
    var barName: String = ""
    var barOpen: Boolean = false
    var barVicinity: String = ""
    var barLat: Double = 0.0
    var barLng: Double = 0.0
    //var barDist: Double = 0.0
    var barRating: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //mainActivity = MainActivity()

        //viewModel = ViewModelProvider(mainActivity).get(HomeViewModel::class.java)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_detail)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)

        val fragment = DetailFragement()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_content_detail, fragment)
            .commit()

        currentFragment = "detail"

        val intent = getIntent()
        barName = intent.getStringExtra("barName").toString()
        barOpen = intent.getBooleanExtra("barOpen", false)
        barRating = intent.getDoubleExtra("barRating", 0.0)
        barVicinity = intent.getStringExtra("barVicinity").toString()
        barLat = intent.getDoubleExtra("barLat", 0.0)
        barLng = intent.getDoubleExtra("barLng", 0.0)
        //barDist = intent.getDoubleExtra("barDist", 0.0)

        //println("debuggage : DetailActivity onCreate")

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_detail)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun goMap(){
        println("debuggage : clicmap")

        viewModel.inMap = true

        currentFragment = "map"

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_content_detail, MapsFragment())
        fragmentTransaction.commit()
    }

    fun goBackHome () {

        /*val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(DetailFragement())
        fragmentTransaction.commit()*/

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        //viewModel.inDetail = false
        currentFragment = "home"
    }

    fun goBackDetail () {

        viewModel.inMap = false

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_content_detail, DetailFragement())
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