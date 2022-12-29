package com.example.projetbar.ui.home

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetbar.Bar
import com.example.projetbar.BuildConfig.GOOGLE_MAPS_API_KEY
import com.example.projetbar.Welcome1
import com.example.projetbar.databinding.ActivityMainBinding
import com.example.projetbar.ui.detail.DetailFragement
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
    var selectedBar: Bar? = null
    var mapLat: Double = 0.0
    var mapLng: Double = 0.0
    //var currentLat: Double = 0.0
    //var currentLng: Double = 0.0
    //var distance: Double = 0.0
    var _detailBarName= MutableLiveData<String>()
    var detailBarName:LiveData<String> =  _detailBarName
    var _detailBarOpen = MutableLiveData<String>()
    var detailBarOpen:LiveData<String> = _detailBarOpen
    var _detailBarRating = MutableLiveData<String>()
    var detailBarRating:LiveData<String> = _detailBarRating
    var _detailBarVicinity = MutableLiveData<String>()
    var detailBarVicinity:LiveData<String> = _detailBarVicinity
    var _detailBarPhoto = MutableLiveData<String>()
    var detailBarPhoto:LiveData<String> = _detailBarPhoto

    var inDetail: Boolean = false

    fun getSelectedBar(bar: Bar) {
        selectedBar = bar

        _detailBarName = MutableLiveData<String>().apply {
            value = bar.name
        }
        detailBarName = _detailBarName


        _detailBarOpen = MutableLiveData<String>().apply {
            value = bar.openingHours.toString()
        }
        detailBarOpen = _detailBarOpen

        _detailBarRating = MutableLiveData<String>().apply {
            value = bar.rating.toString()
        }
        detailBarRating = _detailBarRating

        _detailBarVicinity = MutableLiveData<String>().apply {
            value = bar.vicinity
        }
        detailBarVicinity = _detailBarVicinity

        _detailBarPhoto = MutableLiveData<String>().apply {
            value = bar.photo
        }
        detailBarPhoto = _detailBarPhoto

        getPosition(bar)

        println("maplog : HomeViewModel/getSelectedBar - mapLat = $mapLat , mapLng = $mapLng")
    }

    var listBars = mutableListOf<Bar>()

    val _text = MutableLiveData<String>().apply {
        value = "Liste des Bars :"
    }
    val text: LiveData<String> = _text

    fun initTextView(){
        Log.wtf("wtf", "bar name" + detailBarName.value)
        if(selectedBar != null){
            this._detailBarName.postValue(selectedBar?.name.toString())
            this._detailBarOpen.postValue(selectedBar?.openingHours.toString())
            this._detailBarRating.postValue(selectedBar?.rating.toString())
            this._detailBarVicinity.postValue(selectedBar?.vicinity)

        }

    }

    suspend fun getData(lati: String, longi: String){
        val client = HttpClient(CIO) {
            install(ContentNegotiation){
                json()
            }
        }

        val str = client.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json") {
            url {
                parameters.append("radius", "3000")
                parameters.append("location", "${lati},${longi}")
                parameters.append("type", "bar")
                parameters.append("key", GOOGLE_MAPS_API_KEY)
            }
        }

        println("requête : "+str)

        val gson = Gson()
        val testRes = gson.fromJson(str.bodyAsText(), Welcome1::class.java)

        listBars.clear()

        for (result in testRes.results.iterator()) {
            listBars.add(Bar(result))
        }
        _text.apply {
            value = "Bars proches : " + listBars.size
        }
        _text.postValue("Bars proches : " + listBars.size)

    }

    fun getPosition(bar: Bar) {

        mapLng = bar.lng
        mapLat = bar.lat
        println("maplog : getPosition - mapLat = $mapLat , mapLng = $mapLng")
    }

    suspend fun getPhoto(ref: String): String {
        val client = HttpClient(CIO) {
            install(ContentNegotiation){
                json()
            }
        }

        val str = client.get("https://maps.googleapis.com/maps/api/place/photo") {
            url {
                parameters.append("maxwidth", "400")
                parameters.append("photo_reference", ref)
                parameters.append("key", GOOGLE_MAPS_API_KEY)
            }
        }
        println("xyz: "+str)

        val url = str.bodyAsText(Charsets.UTF_8)

        println("xyz: "+url)

        return url

    }

}