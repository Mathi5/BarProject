package com.example.projetbar.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetbar.Bar
import com.example.projetbar.Welcome1
import com.example.projetbar.databinding.ActivityMainBinding
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*


class HomeViewModel : ViewModel() {

    private lateinit var _binding: ActivityMainBinding
    public lateinit var selectedBar: Bar
    public lateinit var _detailBarName: MutableLiveData<String>
    public lateinit var detailBarName: LiveData<String>
    public lateinit var _detailBarOpen: MutableLiveData<String>
    public lateinit var detailBarOpen: LiveData<String>
    public lateinit var _detailBarRating: MutableLiveData<String>
    public lateinit var detailBarRating: LiveData<String>
    public lateinit var _detailBarVicinity: MutableLiveData<String>
    public lateinit var detailBarVicinity: LiveData<String>

    fun getSelectedBar(bar: Bar) {
        selectedBar = bar

        _detailBarName = MutableLiveData<String>().apply {
            value = bar.name
        }
        detailBarName = _detailBarName

        _detailBarOpen = MutableLiveData<String>().apply {
            if(bar.openingHours != null) {
                value = "ouvert : "+bar.openingHours.toString()
            } else {
                value = "ouvert : info indisponible"
            }
        }
        detailBarOpen = _detailBarOpen

        _detailBarRating = MutableLiveData<String>().apply {
            value = "note : "+bar.rating.toString()
        }
        detailBarRating = _detailBarRating

        _detailBarVicinity = MutableLiveData<String>().apply {
            value = "adresse : "+bar.vicinity
        }
        detailBarVicinity = _detailBarVicinity

        println("infos bar cliqué : $detailBarName , $detailBarOpen , $detailBarRating , $detailBarVicinity" )
    }

    var listBars = mutableListOf<Bar>()

    val _text = MutableLiveData<String>().apply {
        value = "Liste des Bars :"
    }
    val text: LiveData<String> = _text

    suspend fun getData(lati: String, longi: String){
        println("abcd : getData")


        val client = HttpClient(CIO) {
            install(ContentNegotiation){
                json()
            }
        }

        print("nearbysearch test")
        val str = client.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json") {
            url {
                parameters.append("radius", "1500")
                parameters.append("location", "${lati},${longi}")
                parameters.append("type", "bar")
                parameters.append("key", "AIzaSyB6t0WdE2wByUMVO9xP2vCIqiYEKBL0HGo")
            }
        }
        println("url propre : "+str)

        val gson = Gson()
        val testRes = gson.fromJson(str.bodyAsText(), Welcome1::class.java)

        listBars.clear()

        for (result in testRes.results.iterator()) {
            listBars.add(Bar(result))
        }
        _text.apply {
            value = "Liste des bars " + listBars.size
        }
        _text.postValue("Liste des bars " + listBars.size)

    }

}