package com.example.helioapp.webservices_without_retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helioapp.webservices_with_retrofit.ApiInterface
import com.example.helioapp.webservices_with_retrofit.CallbacksRetrofit
import com.example.helioapp.webservices_with_retrofit.ErrorResponseModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

open class BaseViewModel() : ViewModel() {
    val retrofitObject = ApiInterface.getRetrofitObject()

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
                        httpCallback.onSuccessCallback(responseMessage,dataClass)
                    }
                } else {
                    httpCallback.onFailureCallback(responseCode, responseMessage)
                }
            }
        }
    }

    fun <T : Any> retrofitCall(retrofitCall: Call<T>, apiCallBackListener: CallbacksRetrofit) {
        retrofitCall.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.body()?.let { ApiResponse ->
                    apiCallBackListener.onSuccess(ApiResponse)
                }
                response.errorBody()?.let { responseBody ->
                    apiCallBackListener.onFailure(ErrorResponseModel(responseBody.toString()))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                apiCallBackListener.onFailure(ErrorResponseModel(t.toString()))
            }
        })
    }
}