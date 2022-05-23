package com.example.helioapp.webservices_with_retrofit

import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRetrofitViewModel : ViewModel() {

    fun <T : Any> Call(retrofitCall: Call<T>, apiCallBackListener: CallbacksRetrofit) {
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