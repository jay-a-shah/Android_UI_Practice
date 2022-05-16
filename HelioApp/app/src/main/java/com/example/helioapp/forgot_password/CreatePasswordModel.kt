package com.example.helioapp.forgot_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreatePasswordModel: ViewModel() {
    val createPassword: MutableLiveData<String> = MutableLiveData()
    val confirmPassword: MutableLiveData<String> = MutableLiveData()
}