package com.example.helioapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.helioapp.R

@Database(entities = [HotelBookingDataClass::class], version = 2)
abstract class MyRoomDatabaseHelper: RoomDatabase() {
    abstract fun dao(): DataAccessObject

    companion object {
        private var instance: MyRoomDatabaseHelper? = null
        var hotelBookingList: ArrayList<HotelBookingDataClass> = arrayListOf()

        @Synchronized
        fun getInstance(context: Context): MyRoomDatabaseHelper {
            if(instance != null) {
                return instance as MyRoomDatabaseHelper
            }
            synchronized(this){
                val newInstance = Room.databaseBuilder(context,MyRoomDatabaseHelper::class.java,"hotel_database")
                    .fallbackToDestructiveMigration()
                    .build()
                instance = newInstance
                return newInstance
            }
        }
        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: MyRoomDatabaseHelper) {
            val objDoa = db.dao()
            hotelBookingList.apply {
                add(HotelBookingDataClass("President Hotel","Paris, France","4.8","(4,378 reviews)","$35",
                    R.drawable.first_item_image,false,0))
                add(HotelBookingDataClass("Palms Casino","Amsterdam, Netherlands","4.9","(5,283 reviews)","$29",
                    R.drawable.second_item_image,false,1))
                add(HotelBookingDataClass("Palazzo Versace","Rome, Italia","4.7","(3,277 reviews)","$36",
                    R.drawable.third_item_image,false,2))
                add(HotelBookingDataClass("Bulgari Resort","Istanbul, Turkiye","4.8","(4,981 reviews)","$27",
                    R.drawable.fourth_item_image,false,3))
                add(HotelBookingDataClass("Martinez Cannes","London, United Kingdom","4.6","(3,672 reviews)","$32",
                    R.drawable.fifth_item_image,false,4))
            }
            objDoa.insert(hotelBookingList)
        }
    }

}