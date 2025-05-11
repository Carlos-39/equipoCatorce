package com.example.equipocatorce.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.equipocatorce.model.DogAppointment
import com.example.equipocatorce.utils.Constants.NAME_DB

@Database(entities = [DogAppointment::class], version = 1)
abstract class DogAppointmentsDB: RoomDatabase() {
    abstract fun dogAppointmentsDao(): DogAppointmentDao

    companion object{
        fun getDatabase(context: Context): DogAppointmentsDB {
            return  Room.databaseBuilder(
                context.applicationContext,
                DogAppointmentsDB::class.java,
                NAME_DB
            ).build()
        }
    }
}