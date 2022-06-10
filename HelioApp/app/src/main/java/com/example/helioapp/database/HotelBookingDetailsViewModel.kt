package com.example.helioapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelBookingDetailsViewModel(application: Application): AndroidViewModel(application) {

   val readAllData: LiveData<List<HotelBookingDataClass>>
    private val repository: HotelBookingRepository
    init {
        val dao = MyRoomDatabaseHelper.getInstance(application).dao()
        repository = HotelBookingRepository(dao)
        readAllData = repository.readAllData
    }

    fun addData(db: List<HotelBookingDataClass>) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addData(db)
        }
    }
}