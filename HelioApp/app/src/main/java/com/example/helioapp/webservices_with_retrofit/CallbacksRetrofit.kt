package com.example.helioapp.webservices_with_retrofit

interface CallbacksRetrofit {
    fun<T: Any> onSuccess(data: T)
    fun onFailure(error: ErrorResponseModel)
}