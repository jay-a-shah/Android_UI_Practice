package com.example.helioapp.sign_in_screen

import androidx.lifecycle.MutableLiveData
import com.example.helioapp.R
import com.example.helioapp.utils.Constant
import com.example.helioapp.webservices_with_retrofit.ApiInterface
import com.example.helioapp.webservices_with_retrofit.BaseRetrofitViewModel
import com.example.helioapp.webservices_with_retrofit.CallbacksRetrofit
import com.example.helioapp.webservices_with_retrofit.ErrorResponseModel
import com.example.helioapp.webservices_without_retrofit.Callbacks
import org.json.JSONObject
import java.net.URL
import kotlin.math.log

class SignInViewModelWithRetrofit : BaseRetrofitViewModel() {

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
        val retrofit = ApiInterface.getRetrofitObject().logInUser(UserModel(email.value?:"",password.value?:""))
        Call(retrofit, object : CallbacksRetrofit {
            override fun <T : Any> onSuccess(data: T) {
                logInResult.postValue(SignInResponseModel(true, SignInModel::class.java))
            }

            override fun onFailure(error: ErrorResponseModel) {
                logInResult.postValue(SignInResponseModel(false, SignInModel::class.java))
            }
        })
    }
}