package com.example.projetbar

import android.content.Context
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
import com.example.projetbar.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.projetbar.ui.home.HomeViewModel

class MapsFragment : Fragment() {

    private lateinit var _binding: FragmentMapsBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_maps, container, false)
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //SetMap()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            val barSelected = LatLng(viewModel.mapLat, viewModel.mapLng)
            googleMap.addMarker(MarkerOptions().position(barSelected).title(viewModel.selectedBar?.name))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barSelected, 15f))
            println("maplog : map callback - lat = "+viewModel.mapLat+" lng = "+viewModel.mapLng)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivity = context as MainActivity

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
        this.mainActivity.currentFragment = "detail"
    }
}