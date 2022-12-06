package com.example.projetbar.ui.home

import android.net.http.HttpResponseCache.install
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
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
    private var listBars = mutableListOf<Bar>()

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
        Log.wtf("wtf", "Json map " + map["2025-01-01"])


        for (entry in map.entries.iterator()) {
            val BarTmp = Bar(entry.key, entry.value.toString())
            listBars.add(BarTmp)
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