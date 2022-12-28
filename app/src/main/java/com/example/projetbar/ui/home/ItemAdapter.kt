package com.example.projetbar.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetbar.Bar
import com.example.projetbar.Location
import com.example.projetbar.databinding.ItemListeBinding


class ItemAdapter {
    class ItemAdapter(var datalist:List<Bar>, val barCLickInterface:OnBarCLickedListener): RecyclerView.Adapter<ItemAdapter.ItemHolder>() {


        class ItemHolder(val binding: ItemListeBinding , val barCLickedListener: OnBarCLickedListener ) : RecyclerView.ViewHolder(binding.root) {
            private lateinit var viewModel: HomeViewModel

            fun bind(get: Bar) {
                binding.tvItem.text = get.name
                binding.tvItem.setOnClickListener {
                    barCLickedListener.onbarclicked(get)
                }

                /*val crntLocation = Location(viewModel.currentLat, viewModel.currentLng)
                val newLocation = Location(get.lat, get.lng)
                val distance: Float = crntLocation.distanceTo(newLocation) / 1000 // in km
                binding.tvDistance.text = distance.toString()*/

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val binding = ItemListeBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemHolder(binding, barCLickInterface)
        }


        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            holder.bind(datalist.get(position))
        }

        override fun getItemCount(): Int {
            return datalist.size
        }
    }

    interface OnBarCLickedListener{
        fun onbarclicked(bar:Bar)
    }

}