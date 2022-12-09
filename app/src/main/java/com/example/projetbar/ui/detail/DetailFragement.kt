package com.example.projetbar.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.projetbar.MainActivity
import com.example.projetbar.R
import com.example.projetbar.databinding.FragmentDetailFragementBinding
import com.example.projetbar.databinding.FragmentHomeBinding
import com.example.projetbar.ui.home.HomeViewModel

class DetailFragement : Fragment() {

    companion object {
        fun newInstance() = DetailFragement()
    }

    private var _binding: FragmentDetailFragementBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var mainActivity: MainActivity
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val detailViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentDetailFragementBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val textName: TextView = binding.name
        detailViewModel.detailBarName.observe(viewLifecycleOwner) {
            textName.text = it
        }

        val textOpen: TextView = binding.open
        detailViewModel.detailBarOpen.observe(viewLifecycleOwner) {
            textOpen.text = it
        }

        val textRating: TextView = binding.ratings
        detailViewModel.detailBarRating.observe(viewLifecycleOwner) {
            textRating.text = it
        }

        val textVicinity: TextView = binding.vicinity
        detailViewModel.detailBarVicinity.observe(viewLifecycleOwner) {
            textVicinity.text = it
        }

        return inflater.inflate(R.layout.fragment_detail_fragement, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}