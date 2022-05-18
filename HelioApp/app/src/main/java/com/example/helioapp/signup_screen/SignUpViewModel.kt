package com.example.helioapp.signup_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.helioapp.webservices_without_retrofit.HttpCallbackViewModel

class SignUpViewModel: HttpCallbackViewModel() {
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
}