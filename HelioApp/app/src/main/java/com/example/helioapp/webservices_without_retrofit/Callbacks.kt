package com.example.helioapp.webservices_without_retrofit

interface Callbacks {
    fun onSuccessCallback(output:String)
    fun onFailureCallback(responseCode:Int,output:String)
}