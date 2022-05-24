package com.example.helioapp.signup_screen

import androidx.lifecycle.MutableLiveData
import com.example.helioapp.R
import com.example.helioapp.sign_in_screen.UserModel
import com.example.helioapp.utils.Constant.BASEURL
import com.example.helioapp.utils.Constant.KEYEMAIL
import com.example.helioapp.utils.Constant.KEYPASSWORD
import com.example.helioapp.utils.Constant.POSTMETHOD
import com.example.helioapp.utils.Constant.REGISTERURL
import com.example.helioapp.webservices_with_retrofit.ApiInterface
import com.example.helioapp.webservices_with_retrofit.CallbacksRetrofit
import com.example.helioapp.webservices_with_retrofit.ErrorResponseModel
import com.example.helioapp.webservices_without_retrofit.Callbacks
import com.example.helioapp.webservices_without_retrofit.BaseViewModel
import org.json.JSONObject
import java.net.URL

class SignUpViewModel: BaseViewModel(){

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<Int> = MutableLiveData(R.string.name_initial_value)
    val validateData:MutableLiveData<Int> = MutableLiveData()
    val signUpResult = MutableLiveData<SignUpResponseModel>()

    fun performValidation() {
        if (email.value.isNullOrEmpty()) {
            validateData.value = R.string.toast_email_empty
        } else if (password.value.isNullOrEmpty()) {
            validateData.value = R.string.toast_password_empty
        } else {
           retrofitSignUpApiCall()
        }
    }

    private fun signUpapiCall() {
        val credential = JSONObject()
        credential.apply {
            put(KEYEMAIL, email.value)
            put(KEYPASSWORD, password.value)
        }
        val url = URL(BASEURL + REGISTERURL)
        apiCall(credential, url, POSTMETHOD,object : Callbacks {

            override fun <T> onSuccessCallback(output: String, dataClass: T?) {
                signUpResult.postValue(SignUpResponseModel(true,SignUpModel::class.java))
            }

            override fun onFailureCallback(responseCode: Int, output: String) {
                signUpResult.postValue(SignUpResponseModel(false,SignUpModel::class.java))
            }
        },Any::class.java)
    }

    private fun retrofitSignUpApiCall() {
        retrofitCall(retrofitObject.registerUser(UserModel(email.value?:"",password.value?:"")),object : CallbacksRetrofit {
            override fun <T : Any> onSuccess(data: T) {
                signUpResult.postValue(SignUpResponseModel(true,data))
            }

            override fun onFailure(error: ErrorResponseModel) {
                signUpResult.postValue(SignUpResponseModel(false,SignUpModel::class.java))
            }
        })
    }
}

