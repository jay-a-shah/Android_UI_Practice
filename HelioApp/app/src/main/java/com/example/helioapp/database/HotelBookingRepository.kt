package com.example.helioapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao

class HotelBookingRepository(private val dao: DataAccessObject) {

    val readAllData: LiveData<List<HotelBookingDataClass>> = dao.readAllData()

    suspend fun addData(db: List<HotelBookingDataClass>) {
        dao.insert(db)
    }
}