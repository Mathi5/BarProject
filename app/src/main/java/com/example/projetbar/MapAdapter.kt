package com.example.projetbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetbar.databinding.FragmentDetailFragementBinding
import com.example.projetbar.databinding.ItemListeBinding

class MapAdapter {
    class MapAdapter(val mapCLickInterface: com.example.projetbar.ui.home.MapAdapter.OnMapCLickedListener): RecyclerView.Adapter<MapAdapter.ItemHolder>() {


        class MapHolder(val binding: FragmentDetailFragementBinding, val mapCLickedListener: com.example.projetbar.ui.home.MapAdapter.OnMapCLickedListener) : RecyclerView.ViewHolder(binding.root) {
            fun bind(get: Bar) {
                binding.imageMap.setOnClickListener {
                    mapCLickedListener.onmapclicked()
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapHolder {
            val binding = FragmentDetailFragementBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return MapHolder(binding, MapCLickInterface)
        }

    }

    interface OnMapCLickedListener{
        fun onmapclicked()
    }
}