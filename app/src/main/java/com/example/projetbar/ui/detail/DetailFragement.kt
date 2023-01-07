package com.example.projetbar.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.projetbar.MainActivity
import com.example.projetbar.R
import com.example.projetbar.databinding.FragmentDetailFragementBinding
import com.example.projetbar.ui.home.HomeViewModel
import kotlinx.coroutines.launch

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
    private lateinit var photoRef: String
    private lateinit var photoString: String
    private lateinit var photoUri: Uri


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val detailViewModel =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        _binding = FragmentDetailFragementBinding.inflate(inflater, container, false)


        detailViewModel.detailBarName.observe(viewLifecycleOwner) {
            binding.name.text = "Nom : $it"
        }

        val textOpen: TextView = binding.open
        detailViewModel.detailBarOpen.observe(viewLifecycleOwner) {
            if (it == "true" ) {
                textOpen.text = "Ouvert"
            } else if (it == "false"){
                textOpen.text = "Fermé"
            } else {
                textOpen.text = "Horaires inconnue"
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

        detailViewModel.detailBarPhoto.observe(viewLifecycleOwner) {
            photoRef = it
            println("xyz: init photoref")
            lifecycleScope.launch {

                //photoString = viewModel.getPhoto(photoRef)
                //photoUri = Uri.parse(photoString)

                photoUri = Uri.parse("")

                val photoBar: ImageView = binding.imageBar
                //val photoUri: Uri = Uri.parse(photoRef)
                println("xyz: url "+photoUri)
                //photoBar.setImageURI(photoUri)

                Glide.with(mainActivity)
                    .load(photoUri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_no_photo)
                    .error(R.drawable.ic_no_photo)
                    .override(400, 400)
                    .centerCrop()
                    .into(photoBar)
            }
        }

        /*val buttonBack : Button = binding.BackDetailButton

        buttonBack.setOnClickListener {
            mainActivity.goBackHome()
        }*/

        detailViewModel.initTextView()

        return binding.root
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel

        // Compléter l'appel de la map au clic
        println("maplog : activity created")

        btnMap = view?.findViewById(R.id.imageMap) as ImageView
        btnMap.setOnClickListener {
            println("maplog : button clicked")
            mainActivity.goMap()
        }


        //viewModel.getPhoto(photoRef)
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

    override fun onDestroy() {
        super.onDestroy()
        println("debuggage: fragment detail détruit")
    }

}