package com.example.appt.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appt.data.model.UserAppointment
import com.example.appt.data.repository.FirebaseAppointmentManagerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApptViewModel @Inject constructor(
    private val manager: FirebaseAppointmentManagerImpl,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var apptsState by mutableStateOf(AppointmentState())
    var detailState by mutableStateOf(DetailState())

    init {
        getAppointments()
    }

    private fun getAppointments() {
        viewModelScope.launch {
            manager.getAllAppointments(
                onResult = { result ->
                    apptsState = apptsState.copy(appointments = result)
                }
            )
        }
    }

    fun addAppointment(appointment: UserAppointment) {
        viewModelScope.launch {
            manager.addAppointment(appointment)
        }
    }

    fun updateAppointment(name: String, appointment: UserAppointment) {
        viewModelScope.launch {
            manager.updateAppointment(name, appointment)
        }
    }

    fun deleteAppointment(appointment: UserAppointment) {
        viewModelScope.launch {
            manager.deleteAppointment(appointment)
        }
    }

    data class AppointmentState(
        val appointments: List<UserAppointment> = emptyList(),
        val isLoading: Boolean = false,
        val error: String = ""
    )

    data class DetailState(
        val appt: UserAppointment = EMPTY,
        val isEmpty: Boolean = true
    )

    companion object {
        val EMPTY = UserAppointment(
            name = "",
            desc = "",
            date = "",
            hour = "",
            minute = "",
            location = ""
        )
    }
}
