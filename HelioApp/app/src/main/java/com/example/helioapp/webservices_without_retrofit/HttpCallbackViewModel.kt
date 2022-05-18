package com.example.helioapp.webservices_without_retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class HttpCallbackViewModel: ViewModel() {

    fun apiCall(jsonObject: JSONObject, url: URL, request: String, httpCallback: Callbacks){
        viewModelScope.launch(Dispatchers.IO) {
            with(url.openConnection() as HttpURLConnection) {
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
                    httpCallback.onSuccessCallback(responseMessage)
                } else {
                    httpCallback.onFailureCallback(responseCode,responseMessage)
                }
            }
        }
    }
}