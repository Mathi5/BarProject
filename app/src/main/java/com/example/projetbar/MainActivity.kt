package com.example.projetbar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.projetbar.databinding.ActivityMainBinding
import com.example.projetbar.ui.dashboard.DashboardFragment
import com.example.projetbar.ui.detail.DetailFragement
import com.example.projetbar.ui.home.HomeFragment
import com.example.projetbar.ui.home.HomeViewModel
import com.example.projetbar.ui.map.MapsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)


    }

    fun goBar(){
        Log.wtf("wtf", "selected bar " + viewModel.selectedBar?.name )

        //replaceFragment(R.id.nav_host_fragment_activity_main, DetailFragement())

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("barName", viewModel.selectedBar?.name)
        startActivity(intent)

    }

    fun FragmentManager.doTransaction(func: FragmentTransaction.() ->
    FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment){
        supportFragmentManager.doTransaction { add(frameId, fragment) }
    }

    fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment) {
        supportFragmentManager.doTransaction{replace(frameId, fragment)}
    }

    fun AppCompatActivity.removeFragment(fragment: Fragment) {
        supportFragmentManager.doTransaction{remove(fragment)}

    }






}