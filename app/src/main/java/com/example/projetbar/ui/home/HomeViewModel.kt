package com.example.projetbar.ui.home

import android.net.http.HttpResponseCache.install
import android.util.Log
import androidx.lifecycle.LiveData
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

    var num = 0
    private val _text = MutableLiveData<String>().apply {
        value = "Liste des Bars :"
    }
    val text: LiveData<String> = _text

    suspend fun getData(url:String){

        val client = HttpClient(CIO) {
            install(ContentNegotiation){
                json()
            }
        }
        val responseString = client.get(url).bodyAsText()
        var gson = Gson()
        var testRes = gson.fromJson(responseString, Welcome1::class.java)

        for (result in testRes.results.iterator()) {

            val Bartmp = Bar(result)

            println("Bar : "+Bartmp)
        }



    }

    fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
        when (val value = this[it])
        {
            is JSONArray ->
            {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                JSONObject(map).toMap().values.toList()
            }
            is JSONObject -> value.toMap()
            JSONObject.NULL -> null
            else            -> value
        }
    }
}