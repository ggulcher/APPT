package com.example.appt.data.repository

import com.example.appt.data.model.UserAppointment

interface FirebaseAppointmentManager {

    suspend fun addAppointment(appt: UserAppointment)

    suspend fun updateAppointment(name: String, appt: UserAppointment)

    suspend fun deleteAppointment(appt: UserAppointment)

    suspend fun getAllAppointments(onResult: (List<UserAppointment>) -> Unit)
}
