package com.example.projetbar.ui.home

import android.annotation.SuppressLint
import android.location.Location
import android.system.Os.remove
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.projetbar.Bar
import com.example.projetbar.databinding.ItemListeBinding


class ItemAdapter {
    class ItemAdapter(var datalist:List<Bar>, val barCLickInterface:OnBarCLickedListener): RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

        class ItemHolder(val binding: ItemListeBinding , val barCLickedListener: OnBarCLickedListener) : RecyclerView.ViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(get: Bar) {
                binding.tvItem.text = get.name
                binding.tvItem.setOnClickListener {
                    barCLickedListener.onbarclicked(get)
                }
                binding.tvDistance.text = get.distance.toString()+" Km"
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

        /*fun clear() {
            val size = itemCount
            var i = 0
            if (size > 0) {
                while (i < size) {
                    ItemListeBinding.remove(0)
                    i++
                }
            }
        }*/
    }

    interface OnBarCLickedListener{
        fun onbarclicked(bar:Bar)
    }

}