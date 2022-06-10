package com.example.helioapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataAccessObject {

    @Insert(onConflict = OnConflictStrategy.IGNORE)//This Ignores Same Records
    fun insert(db: List<HotelBookingDataClass>)

    @Update
    fun update(db: HotelBookingDataClass)

    @Delete
    fun delete(db: HotelBookingDataClass)

    @Query("delete from hotel_booking_detail")
    fun deleteAllData()

    @Query("SELECT * FROM hotel_booking_detail")
    fun readAllData(): LiveData<List<HotelBookingDataClass>>
}