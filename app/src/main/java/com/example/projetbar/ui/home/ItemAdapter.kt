package com.example.projetbar.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetbar.Bar
import com.example.projetbar.databinding.ItemListeBinding


class ItemAdapter {
    class ItemAdapter(var datalist:List<Bar>): RecyclerView.Adapter<ItemAdapter.ItemHolder>() {


        class ItemHolder(val binding: ItemListeBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(get: Bar) {
                binding.tvItem.text = get.name
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val binding = ItemListeBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemHolder(binding)
        }


        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            holder.bind(datalist.get(position))
        }

        override fun getItemCount(): Int {
            return datalist.size
        }
    }
}