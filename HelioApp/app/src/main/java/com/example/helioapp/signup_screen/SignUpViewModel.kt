package com.example.helioapp.signup_screen

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helioapp.R
import com.example.helioapp.utils.Constant
import com.example.helioapp.webservices_without_retrofit.Callbacks
import com.example.helioapp.webservices_without_retrofit.HttpCallbackViewModel
import org.json.JSONObject
import java.net.URL

class SignUpViewModel(application: Application): HttpCallbackViewModel(application){

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData("Test123")
    fun getSignUpResult(): LiveData<String> = signUpResult
    private val signUpResult = MutableLiveData<String>()

    fun performValidation() {
        if (email.value.isNullOrEmpty()) {
            signUpResult.value = getApplication<Application>().resources.getString(R.string.toast_email_empty)
        } else if (password.value.isNullOrEmpty()) {
            signUpResult.value = getApplication<Application>().resources.getString(R.string.toast_password_empty)
        } else {
            apiCall()
        }
    }
    fun apiCall() {
        val credential = JSONObject()
        credential.apply {
            put("email", email.value)
            put("password", password.value)
            put("name",name.value)
        }
        val url = URL(Constant.BASEURL)
        apiCall(credential, url, "POST",object : Callbacks {
            override fun onSuccessCallback(output: String) {
                signUpResult.postValue(getApplication<Application>().resources.getString(R.string.user_create))
            }
            override fun onFailureCallback(responseCode: Int, output: String) {
                signUpResult.postValue(getApplication<Application>().resources.getString(R.string.user_not_created))
            }
        },Any::class.java)
    }
}

