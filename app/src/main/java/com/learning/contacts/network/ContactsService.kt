package com.learning.contacts.network

import com.learning.contacts.model.Contact
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ContactsService {

    @GET("/contacts")
    suspend fun getContacts(): Response<List<Contact>>

    @POST("/contacts")
    suspend fun postContacts(@Body contact: Contact)

    companion object {
        const val CONTACTS_SERVICE_BASE_URL = "http://167.172.6.138:8080"
    }
}