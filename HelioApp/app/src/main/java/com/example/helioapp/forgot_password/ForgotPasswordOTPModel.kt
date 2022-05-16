package com.example.helioapp.forgot_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForgotPasswordOTPModel: ViewModel() {
    val etOne: MutableLiveData<String> = MutableLiveData()
    val etTwo: MutableLiveData<String> = MutableLiveData()
    val etThree: MutableLiveData<String> = MutableLiveData()
    val etFour: MutableLiveData<String> = MutableLiveData()
}