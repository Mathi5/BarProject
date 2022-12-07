package com.example.projetbar.ui.home

import android.net.http.HttpResponseCache.install
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetbar.Bar
import com.example.projetbar.databinding.ActivityMainBinding
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
        value = num.toString()
    }
    val text: LiveData<String> = _text

    suspend fun getData(url:String){

        val client = HttpClient(CIO) {
            install(ContentNegotiation){
                json()
            }
        }
        val responseString = client.get(url).bodyAsText()
        val jsonObj = JSONObject(responseString)
        val map = jsonObj.toMap()
        Log.wtf("wtf", map.toString())


        val barTmp = Bar(map)
        listBars.add(barTmp)

        for (entry in map.entries.iterator()) {
            if (entry.key == "results") {
                Log.wtf("wtf", entry.key)
                val testRes = JSONObject(entry.value.toString())
                val jsonTestRes = testRes.toMap()
                Log.wtf("wtf", jsonTestRes.toString())
            }
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