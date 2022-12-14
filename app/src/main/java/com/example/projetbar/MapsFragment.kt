package com.example.projetbar

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.projetbar.databinding.ActivityMainBinding.inflate
import com.example.projetbar.databinding.FragmentDashboardBinding.inflate
import com.example.projetbar.databinding.FragmentDetailFragementBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.projetbar.ui.home.HomeViewModel

class MapsFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var mMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        val barSelected = LatLng(viewModel.mapLat, viewModel.mapLng)
        googleMap.addMarker(MarkerOptions().position(barSelected).title("Marker on selected bar"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(barSelected))
        println("maplog : map callback - lat = "+viewModel.mapLat+" lng = "+viewModel.mapLng)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //MapsFragment.getMapAsync(this)
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        println("maplog : map loaded")
    }
}