package com.example.appt.di

import com.example.appt.data.repository.FirebaseAppointmentManager
import com.example.appt.data.repository.FirebaseAppointmentManagerImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun providesFireStore() = Firebase.firestore

    @Provides
    @Singleton
    fun providesFirebaseRepository(
        firebaseAuth: FirebaseAuth,
        fireStore: FirebaseFirestore
    ): FirebaseAppointmentManager {
        return FirebaseAppointmentManagerImpl(firebaseAuth, fireStore)
    }
}
