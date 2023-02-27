package com.example.appt.data.repository

import com.example.appt.data.model.UserAppointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class FirebaseAppointmentManagerImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseAppointmentManager {

    private val appointmentRef = firestore.collection(FIREBASE_COLLECTION)

    override suspend fun getAllAppointments(
        onResult: (List<UserAppointment>) -> Unit
    ) {
        appointmentRef.addSnapshotListener { querySnapshot, error ->
            error.let {
                Timber.e(it)
            }
            querySnapshot?.let {
                onResult(
                    it.map { doc -> doc.toObject(UserAppointment::class.java) })
            }
        }
    }

    override suspend fun addAppointment(appt: UserAppointment) {
        val query = appointmentRef
            .whereEqualTo("name", appt.name)
            .get()
            .await()

        if (query.isEmpty) {
            try {
                appointmentRef.add(appt)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { Timber.e(e) }
            }
        }
    }

    override suspend fun updateAppointment(name: String, appt: UserAppointment) {
        val oldAppt = appointmentRef
            .whereEqualTo("name", name)
            .get()
            .await()
        val updateAppt = hashMapOf(
            "name" to appt.name,
            "desc" to appt.desc,
            "date" to appt.date,
            "hour" to appt.hour,
            "minute" to appt.minute,
            "location" to appt.location
        )

        if(oldAppt.documents.isNotEmpty()) {
            for(document in oldAppt) {
                try {
                    appointmentRef.document(document.id).set(
                        updateAppt,
                        SetOptions.merge()
                    ).await()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) { Timber.e(e) }
                }
            }
        } else {
            withContext(Dispatchers.Main) {}
        }
    }

    override suspend fun deleteAppointment(appt: UserAppointment) {
        val query = appointmentRef
            .whereEqualTo("name", appt.name)
            .get()
            .await()
        if (query.documents.isNotEmpty()) {
            for (document in query) {
                try {
                    appointmentRef.document(document.id).delete().await()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) { Timber.e(e) }
                }
            }
        }
    }

    companion object {
        const val FIREBASE_COLLECTION = "user_appointments"
    }
}
