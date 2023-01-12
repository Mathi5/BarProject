package com.example.projetbar.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
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
import com.example.projetbar.R
import com.example.projetbar.databinding.FragmentHomeBinding
import com.example.projetbar.ui.detail.DetailFragement
import kotlinx.coroutines.launch
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class HomeFragment : Fragment() , ItemAdapter.OnBarCLickedListener {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var itemAdapter : ItemAdapter.ItemAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mainActivity: MainActivity
    private var isAlreadyCreate = false
    private val LOCATION_PERMISSION_REQUEST = 1


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val mListBars = mutableListOf<Bar>()
        //mListBars.clear()
        itemAdapter = ItemAdapter.ItemAdapter(mListBars,this)
        binding.rvItem.adapter = itemAdapter

        binding.rvItem.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        //getLastKnownLocation()

        println("backbutton: onCreateView")

        this.mainActivity.currentFragment = "home"

        return root
    }

    fun requestGoogle(lat: String, long: String){
        println("abcd : requestGoogle")

        lifecycleScope.launch {
            viewModel.getData(lat, long)
            itemAdapter.datalist = viewModel.listBars
            itemAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        getLastKnownLocation()
    }

    fun getLastKnownLocation() {
        println("abcd : getLastKnownLocation")
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
            println("abcd : pas de permission")
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    // use your location object
                    // get latitude , longitude and other info from this
                    val lat = location.latitude.toFloat().toString()
                    val long = location.longitude.toFloat().toString()
                    viewModel.currentLoc = location
                    viewModel.currentLat = location.latitude
                    viewModel.currentLng = location.longitude

                    requestGoogle(lat, long)
                } else {
                    println("abcd : pas de localisation")
                }

            }

    }

    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        println("abcd : résultat permission")
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    println("abcd : permission obtenue")
                    //getLastKnownLocation()
                } else {
                    println("abcd : toujours pas de permission")
                }
                return
            }
        }
    }*/

    override fun onbarclicked(bar: Bar) {
        if (!viewModel.inDetail){
            viewModel.inDetail = true
            viewModel.clicOrigin = "list"
            Log.wtf("wtf", "bar name: " + bar.name)
            viewModel.getSelectedBar(bar)
            mainActivity.goBar()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        println("debuggage: fragment home détruit")
    }

    }

