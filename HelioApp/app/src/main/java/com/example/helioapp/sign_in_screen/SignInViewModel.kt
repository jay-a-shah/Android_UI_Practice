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

class SignInViewModel() : HttpCallbackViewModel() {

    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val logInResult = MutableLiveData<SignInResponseModel>()
    val validateData: MutableLiveData<Int> = MutableLiveData()

    fun performValidation() {
        if (email.value.isNullOrEmpty()) {
            validateData.value = R.string.toast_email_empty
        } else if (password.value.isNullOrEmpty()) {
            validateData.value = R.string.toast_password_empty
        } else {
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
        apiCall(credential, url, Constant.POSTMETHOD, object : Callbacks {
            override fun <T> onSuccessCallback(output: String, dataClass: T?) {
                logInResult.postValue(SignInResponseModel(true, SignInModel::class.java))
            }

            override fun onFailureCallback(responseCode: Int, output: String) {
                logInResult.postValue(SignInResponseModel(false, Any::class.java))
            }
        }, Any::class.java)
    }
}