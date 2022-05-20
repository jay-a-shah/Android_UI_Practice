package com.example.helioapp.webservices_without_retrofit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

open class HttpCallbackViewModel(application: Application) : AndroidViewModel(application) {

    fun <T> apiCall(jsonObject: JSONObject, url: URL, request: String, httpCallback: Callbacks, modelClass: Class<T>? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val httpURLConnection = url.openConnection() as HttpURLConnection
            with(httpURLConnection) {
                requestMethod = request
                val cred = jsonObject
                setRequestProperty("Content-Type", "application/json")
                val writer = OutputStreamWriter(outputStream)
                writer.write(cred.toString())
                writer.flush()
                println("URL : $url")
                println("cred: $cred")
                println("Response Code: $responseCode")
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = httpURLConnection.inputStream.bufferedReader()
                        .use { it.readText() }
                    withContext(Dispatchers.Main) {
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        var dataClass = gson.fromJson(response, modelClass)
                        httpCallback.onSuccessCallback(responseMessage)
                    }
                } else {
                    httpCallback.onFailureCallback(responseCode, responseMessage)
                }
            }
        }
    }
}