package com.example.helioapp.webservices_without_retrofit

interface Callbacks {
    fun<T> onSuccessCallback(output:String, dataClass:T? = null)
    fun onFailureCallback(responseCode:Int,output:String)
}