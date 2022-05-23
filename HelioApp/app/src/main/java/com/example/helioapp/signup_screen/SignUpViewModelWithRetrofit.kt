package com.example.helioapp.signup_screen

import androidx.lifecycle.MutableLiveData
import com.example.helioapp.R
import com.example.helioapp.sign_in_screen.SignInModel
import com.example.helioapp.sign_in_screen.SignInResponseModel
import com.example.helioapp.sign_in_screen.UserModel
import com.example.helioapp.utils.Constant
import com.example.helioapp.webservices_with_retrofit.ApiInterface
import com.example.helioapp.webservices_with_retrofit.BaseRetrofitViewModel
import com.example.helioapp.webservices_with_retrofit.CallbacksRetrofit
import com.example.helioapp.webservices_with_retrofit.ErrorResponseModel
import com.example.helioapp.webservices_without_retrofit.Callbacks
import org.json.JSONObject
import java.net.URL

class SignUpViewModelWithRetrofit: BaseRetrofitViewModel() {

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val validateData: MutableLiveData<Int> = MutableLiveData()
    val signUpResult = MutableLiveData<SignUpResponseModel>()

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
       val retrofit = ApiInterface.getRetrofitObject().registerUser(UserModel(email.value?:"",password.value?:""))
        Call(retrofit,object : CallbacksRetrofit{
            override fun <T : Any> onSuccess(data: T) {
                signUpResult.postValue(SignUpResponseModel(true,data))
            }

            override fun onFailure(error: ErrorResponseModel) {
               signUpResult.postValue(SignUpResponseModel(false,SignUpModel::class.java))
            }

        })
    }

}