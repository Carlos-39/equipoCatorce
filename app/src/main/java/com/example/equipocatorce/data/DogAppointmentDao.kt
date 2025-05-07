package com.example.equipocatorce.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.equipocatorce.model.DogAppointment

@Dao
interface DogAppointmentDao {
    @Query("SELECT * FROM DogAppointment")
    suspend fun getAppointments(): MutableList<DogAppointment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAppointment(dogAppointment: DogAppointment)

    @Delete
    suspend fun deleteAppointment(dogAppointment: DogAppointment)

    @Update
    suspend fun updateAppointment(dogAppointment: DogAppointment)
}