package com.example.projetbar.ui.map

import android.content.Context
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.projetbar.MainActivity
import com.example.projetbar.R
import com.example.projetbar.databinding.FragmentMapsBinding
import com.example.projetbar.ui.detail.DetailFragement

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.projetbar.ui.home.HomeViewModel

class MapsFragment : Fragment() {

    companion object {
        fun newInstance() = MapsFragment()
    }

    private var _binding: FragmentMapsBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        //SetMap()
        this.mainActivity.currentFragment = "map"

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            val barSelected = LatLng(viewModel.mapLat, viewModel.mapLng)
            googleMap.addMarker(MarkerOptions().position(barSelected).title(viewModel.selectedBar?.name))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barSelected, 15f))
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivity = context as MainActivity

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //this.mainActivity.currentFragment = "detail"
    }

    override fun onDestroy() {
        super.onDestroy()
        println("debuggage: fragment map détruit")
    }
}