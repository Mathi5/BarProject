package com.example.projetbar.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.projetbar.MainActivity
import com.example.projetbar.R
import com.example.projetbar.databinding.FragmentDashboardBinding
import com.example.projetbar.ui.home.HomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class DashboardFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    private var _binding: FragmentDashboardBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val barMapsFragment = childFragmentManager.findFragmentById(R.id.dashboard_fragment) as SupportMapFragment
        barMapsFragment.getMapAsync { googleMap ->

            googleMap.setOnMarkerClickListener(this)

            for (bar in viewModel.listBars) {
                var barSelected = LatLng(bar.lat, bar.lng)
                googleMap.addMarker(MarkerOptions()
                    .position(barSelected)
                    .title(bar.name)
                )
            }

            val currentPos = LatLng(viewModel.currentLat, viewModel.currentLng)
            googleMap.addMarker(MarkerOptions()
                .position(currentPos)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Vous")
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 12f))
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        if (marker.title != "Vous") {
            val selectedBar = marker.title?.let { viewModel.getBarByName(it) }
            if (!viewModel.inDetail){
                viewModel.inDetail = true
                viewModel.clicOrigin = "map"
                if (selectedBar != null) {
                    viewModel.getSelectedBar(selectedBar)
                }
                mainActivity.goBar()
            }
        }
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivity = context as MainActivity

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}