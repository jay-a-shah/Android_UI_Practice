package com.example.helioapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.helioapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelBookingDetailsViewModel(application: Application): AndroidViewModel(application) {

   val readAllData: LiveData<List<HotelBookingDataClass>>
    private val repository: HotelBookingRepository
    var hotelBookingList: ArrayList<HotelBookingDataClass> = arrayListOf()

    init {
        val dao = MyRoomDatabaseHelper.getInstance(application).dao()
        repository = HotelBookingRepository(dao)
        readAllData = repository.readAllData
    }

    fun addData() {
        viewModelScope.launch(Dispatchers.IO){
            repository.addData(getList())
        }
    }

    fun updateData(itemPosition: Int?, bookmarkValue: Boolean) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateData(itemPosition,bookmarkValue)
        }
    }
    private fun getList(): List<HotelBookingDataClass> {
        hotelBookingList.apply {
            add(HotelBookingDataClass("President Hotel","Paris, France","4.8","(4,378 reviews)","$35",
                R.drawable.first_item_image,false,0))
            add(HotelBookingDataClass("Palms Casino","Amsterdam, Netherlands","4.9","(5,283 reviews)","$29", R.drawable.second_item_image,false,1))
            add(HotelBookingDataClass("Palazzo Versace","Rome, Italia","4.7","(3,277 reviews)","$36", R.drawable.third_item_image,false,2))
            add(HotelBookingDataClass("Bulgari Resort","Istanbul, Turkiye","4.8","(4,981 reviews)","$27", R.drawable.fourth_item_image,false,3))
            add(HotelBookingDataClass("Martinez Cannes","London, United Kingdom","4.6","(3,672 reviews)","$32", R.drawable.fifth_item_image,false,4))
        }
        return hotelBookingList
    }
}