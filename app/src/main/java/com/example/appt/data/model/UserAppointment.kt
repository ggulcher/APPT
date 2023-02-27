package com.example.appt.data.model

data class UserAppointment(
    var name: String = "",
    var desc: String = "",
    val date: String = "",
    val hour: String = "",
    val minute: String = "",
    val location: String = ""
)
