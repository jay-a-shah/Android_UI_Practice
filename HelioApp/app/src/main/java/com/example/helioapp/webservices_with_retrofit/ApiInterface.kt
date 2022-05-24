package com.example.helioapp.webservices_with_retrofit

import com.example.helioapp.BuildConfig
import com.example.helioapp.sign_in_screen.UserModel
import com.example.helioapp.signup_screen.SignUpModel
import com.example.helioapp.utils.Constant.BASEURL
import com.example.helioapp.utils.Constant.LOGINURL
import com.example.helioapp.utils.Constant.REGISTERURL
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST(REGISTERURL)
    fun registerUser(@Body userData: UserModel): Call<SignUpModel>

    @POST(LOGINURL)
    fun logInUser(@Body userData: UserModel): Call<ErrorResponseModel>

    companion object {
        private val okHttpClientBuilder = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(OkHttpProfilerInterceptor())
            }
        }

        fun getRetrofitObject(): ApiInterface{
            val retrofit = Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(
                GsonConverterFactory.create())
                .client(okHttpClientBuilder.build()).build().create(ApiInterface::class.java)
            return retrofit
        }
    }
}

