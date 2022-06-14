package com.example.helioapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.helioapp.R

@Database(entities = [HotelBookingDataClass::class], version = 3)
abstract class MyRoomDatabaseHelper: RoomDatabase() {
    abstract fun dao(): DataAccessObject

    companion object {
        private var instance: MyRoomDatabaseHelper? = null


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
    }

}