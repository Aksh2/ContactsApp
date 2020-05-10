package com.learning.contacts.di.module

import com.learning.contacts.network.ContactsService
import com.learning.contacts.network.ContactsService.Companion.CONTACTS_SERVICE_BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): ContactsService = Retrofit.Builder()
        .baseUrl(CONTACTS_SERVICE_BASE_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()
        .create(ContactsService::class.java)
}