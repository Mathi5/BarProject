package com.example.projetbar.ui.home

import android.net.http.HttpResponseCache.install
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetbar.Bar
import com.example.projetbar.Welcome1
import com.example.projetbar.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private lateinit var _binding: ActivityMainBinding
    var listBars = mutableListOf<Bar>()
    var lat: String = ""
    var long: String = ""


    /*var localisation: MediatorLiveData<Pair<LiveData<String>?, LiveData<String>?>> = object: MediatorLiveData<Pair<LiveData<String>?, LiveData<String>?>>() {
        var lat: LiveData<String>? = null
        var long: LiveData<String>? = null
        init {
            addSource(this.lat) { lat ->
                this.lat = lat
            }

        }*/


    val _text = MutableLiveData<String>().apply {
        value = "Liste des Bars :"
    }
    val text: LiveData<String> = _text
    val _textLat = MutableLiveData<String>().apply {
        value = ""
    }
    val textLat: LiveData<String> = _textLat


    suspend fun getData(lati: String, longi: String){

        val client = HttpClient(CIO) {
            install(ContentNegotiation){
                json()
            }
        }
        //val responseString = client.get(url).bodyAsText()
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

    fun getLocalisation(x : String, y : String) {
        lat = x
        long = y
        _textLat.postValue(lat)
        Log.wtf("wtf" , "has obs " + textLat.hasObservers())
    }

}