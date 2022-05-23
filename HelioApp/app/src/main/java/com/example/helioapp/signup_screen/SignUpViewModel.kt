package com.example.helioapp.signup_screen

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.helioapp.R
import com.example.helioapp.utils.Constant
import com.example.helioapp.utils.Constant.BASEURL
import com.example.helioapp.utils.Constant.KEYEMAIL
import com.example.helioapp.utils.Constant.KEYNAME
import com.example.helioapp.utils.Constant.KEYPASSWORD
import com.example.helioapp.utils.Constant.POSTMETHOD
import com.example.helioapp.utils.Constant.REGISTERURL
import com.example.helioapp.webservices_without_retrofit.Callbacks
import com.example.helioapp.webservices_without_retrofit.HttpCallbackViewModel
import org.json.JSONObject
import java.net.URL

class SignUpViewModel(): HttpCallbackViewModel(){

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<Int> = MutableLiveData(R.string.name_initial_value)
    val signUpResult = MutableLiveData<Int>()

    fun performValidation() {
        if (email.value.isNullOrEmpty()) {
            signUpResult.value = R.string.toast_email_empty
        } else if (password.value.isNullOrEmpty()) {
            signUpResult.value = R.string.toast_password_empty
        } else {
            apiCall()
        }
    }

    fun apiCall() {
        val credential = JSONObject()
        credential.apply {
            put(KEYEMAIL, email.value)
            put(KEYPASSWORD, password.value)
        }
        val url = URL(BASEURL + REGISTERURL)
        apiCall(credential, url, POSTMETHOD,object : Callbacks {
            override fun onSuccessCallback(output: String) {
                signUpResult.postValue(R.string.user_create)
            }
            override fun onFailureCallback(responseCode: Int, output: String) {
                signUpResult.postValue(R.string.user_not_created)
            }
        },Any::class.java)
    }
}

