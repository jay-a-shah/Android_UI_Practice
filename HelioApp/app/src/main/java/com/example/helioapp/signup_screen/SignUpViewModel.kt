package com.example.helioapp.signup_screen

import androidx.lifecycle.MutableLiveData

class SignUpViewModel {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
}