package com.example.helioapp.sign_in_screen

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helioapp.R
import com.example.helioapp.utils.Constant
import com.example.helioapp.utils.Constant.BASEURL
import com.example.helioapp.utils.Constant.LOGINURL
import com.example.helioapp.webservices_without_retrofit.Callbacks
import com.example.helioapp.webservices_without_retrofit.HttpCallbackViewModel
import org.json.JSONObject
import java.net.URL

class SignInViewModel(application: Application): HttpCallbackViewModel(application) {

    val progressBarStatus: MutableLiveData<Boolean> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    private val logInResult = MutableLiveData<String>()

    fun performValidation() {
        if (email.value.isNullOrEmpty()) {
            logInResult.value = getApplication<Application>().resources.getString(R.string.toast_email_empty)
        } else if (password.value.isNullOrEmpty()) {
            logInResult.value = getApplication<Application>().resources.getString(R.string.toast_password_empty)
        } else {
            progressBarStatus.value = true
            apiCall()
        }
    }

    private fun apiCall() {
        val credential = JSONObject()
        credential.apply {
            put(Constant.KEYEMAIL, email.value)
            put(Constant.KEYPASSWORD, password.value)
        }
        val url = URL(BASEURL + LOGINURL)
        apiCall(credential, url, Constant.POSTMETHOD,object : Callbacks {
            override fun onSuccessCallback(output: String) {
                progressBarStatus.postValue(false)
                logInResult.postValue(output)
            }
            override fun onFailureCallback(responseCode: Int, output: String) {
                progressBarStatus.postValue(false)
                logInResult.postValue(getApplication<Application>().resources.getString(R.string.login_not_successfull))
            }
        },Any::class.java)
    }
}