package com.example.helioapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotel_booking_detail")
data class HotelBookingDataClass(
    val hotelName: String,
    val place: String,
    val rating: String,
    val noOfReviews: String,
    val price: String,
    val hotelImage: Int,
    val bookmarkValue: Boolean = false,
    @PrimaryKey(autoGenerate = false) val id: Int? = null
)