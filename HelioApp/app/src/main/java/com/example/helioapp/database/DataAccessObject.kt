package com.example.helioapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataAccessObject {

    @Insert(onConflict = OnConflictStrategy.IGNORE)//This Ignores Same Records
    fun insert(db: List<HotelBookingDataClass>)

    @Query("UPDATE hotel_booking_detail SET bookmarkValue = :bookmarkValue WHERE id = :id")
    fun updateItem(id:Int?, bookmarkValue:Boolean)

    @Delete
    fun delete(db: HotelBookingDataClass)

    @Query("delete from hotel_booking_detail")
    fun deleteAllData()

    @Query("SELECT * FROM hotel_booking_detail")
    fun readAllData(): LiveData<List<HotelBookingDataClass>>
}