package com.example.projetbar.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetbar.Bar
import com.example.projetbar.MainActivity
import com.example.projetbar.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class HomeFragment : Fragment() , ItemAdapter.OnBarCLickedListener {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var itemAdapter : ItemAdapter.ItemAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mainActivity: MainActivity


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        homeViewModel.text.observe(viewLifecycleOwner) {
        }
        var mListBars = mutableListOf<Bar>()
        //binding.listeBar.adapter.
        itemAdapter = ItemAdapter.ItemAdapter(mListBars,this)
        binding.rvItem.adapter = itemAdapter

        binding.rvItem.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastKnownLocation()



        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivity = context as MainActivity

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)




    }

    fun requestGoogle(lat: String, long: String){
        lifecycleScope.launch {
            //val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${lat}%2C${long}&radius=1500&type=bar&key=AIzaSyB6t0WdE2wByUMVO9xP2vCIqiYEKBL0HGo"


            //var url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522%2C151.1957362&radius=1500&type=bar&key=AIzaSyB6t0WdE2wByUMVO9xP2vCIqiYEKBL0HGo"
            //println(url)
            viewModel.getData(lat, long)
            itemAdapter.datalist = viewModel.listBars
            itemAdapter.notifyDataSetChanged()
            //println("url récupéré, latitude: ${lat}, longitude: ${long}")
        }
    }

    override fun onResume() {
        super.onResume()

    }

    fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            println("localisation : pas de permission")
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    // use your location object
                    // get latitude , longitude and other info from this
                    val lat = location.latitude.toFloat().toString()
                    val long = location.longitude.toFloat().toString()
                    println("localisation : latitude : $lat, longitude : $long")
                    requestGoogle(lat, long)
                } else {
                    println("pas de localisation")
                }

            }

    }

    override fun onbarlicked(bar: Bar) {
        Log.wtf("wtf", "bar name: " + bar.name)
        mainActivity.goToBar(bar)
    }

}