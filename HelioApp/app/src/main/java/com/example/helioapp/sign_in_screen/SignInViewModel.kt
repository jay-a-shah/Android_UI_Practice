package com.example.helioapp.sign_in_screen

import androidx.lifecycle.MutableLiveData
import com.example.helioapp.R
import com.example.helioapp.utils.Constant
import com.example.helioapp.utils.Constant.BASEURL
import com.example.helioapp.utils.Constant.LOGINURL
import com.example.helioapp.webservices_with_retrofit.ApiInterface
import com.example.helioapp.webservices_with_retrofit.CallbacksRetrofit
import com.example.helioapp.webservices_with_retrofit.ErrorResponseModel
import com.example.helioapp.webservices_without_retrofit.Callbacks
import com.example.helioapp.webservices_without_retrofit.BaseViewModel
import org.json.JSONObject
import java.net.URL

class SignInViewModel() : BaseViewModel() {

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
            retrofitSignInApiCall()
        }
    }

    private fun signInApiCall() {
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

    private fun retrofitSignInApiCall() {
        retrofitCall(
            retrofitObject.logInUser(UserModel(email.value ?: "", password.value ?: "")),
            object : CallbacksRetrofit {
                override fun <T : Any> onSuccess(data: T) {
                    logInResult.postValue(SignInResponseModel(true, SignInModel::class.java))
                }

                override fun onFailure(error: ErrorResponseModel) {
                    logInResult.postValue(SignInResponseModel(false, SignInModel::class.java))
                }
            })
    }
}