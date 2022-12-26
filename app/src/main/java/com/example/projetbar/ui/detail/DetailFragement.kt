package com.example.projetbar.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.projetbar.Bar
import com.example.projetbar.MainActivity
import com.example.projetbar.R
import com.example.projetbar.databinding.FragmentDetailFragementBinding
import com.example.projetbar.databinding.FragmentHomeBinding
import com.example.projetbar.databinding.ItemListeBinding
import com.example.projetbar.ui.home.HomeViewModel
import com.example.projetbar.ui.home.ItemAdapter

class DetailFragement : Fragment() {

    companion object {
        fun newInstance() = DetailFragement()
    }

    private var _binding: FragmentDetailFragementBinding? = null
    private lateinit var viewModel: HomeViewModel
    //private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity
    private val binding get() = _binding!!
    private lateinit var btnMap: ImageView


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val detailViewModel =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        _binding = FragmentDetailFragementBinding.inflate(inflater, container, false)

        val root: View = binding.root


        detailViewModel.detailBarName.observe(viewLifecycleOwner) {
            binding.name.text = "Nom : $it"
        }

        val textOpen: TextView = binding.open
        detailViewModel.detailBarOpen.observe(viewLifecycleOwner) {
            if (it != null ) {
                textOpen.text = "Horaires inconnue"
            } else {
                textOpen.text = it
            }
        }

        val textRating: TextView = binding.ratings
        detailViewModel.detailBarRating.observe(viewLifecycleOwner) {
            textRating.text = "note : $it"
        }

        val textVicinity: TextView = binding.vicinity
        detailViewModel.detailBarVicinity.observe(viewLifecycleOwner) {
            textVicinity.text = "adresse : $it"

        }


        val buttonBack : Button = binding.BackDetailButton

        buttonBack.setOnClickListener {
            mainActivity.goBackHome()
        }

        detailViewModel.initTextView()
        return binding.root
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        //mainActivity = MainActivity()
        // TODO: Use the ViewModel

        // Compléter l'appel de la map au clic
        println("maplog : activity created")
        btnMap = view?.findViewById(R.id.imageMap) as ImageView
        btnMap.setOnClickListener {
            println("maplog : button clicked")
            mainActivity.goMap()
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivity = context as MainActivity

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //this.mainActivity.currentFragment = "home"
    }


}