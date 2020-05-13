package com.learning.contacts.data.repository

import android.util.Log
import com.learning.contacts.data.database.ContactsDao
import com.learning.contacts.model.Contact
import com.learning.contacts.network.ContactsService
import com.learning.contacts.network.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val contactsDao: ContactsDao,
    private val contactsService: ContactsService
) {
    private val TAG = ContactRepository::class.java.simpleName
    fun getAllContacts(): Flow<ResponseState<List<Contact>>> {

        return object : NetworkBoundRepository<List<Contact>, List<Contact>>(){
            override suspend fun saveRemoteData(response: List<Contact>) {
                contactsDao.insertContactList(response)
            }

            override fun fetchFromLocal(): Flow<List<Contact>> =
                contactsDao.getAllContacts()

            override suspend fun fetchFromRemote(): Response<List<Contact>> = contactsService.getContacts()
        }.asFlow().flowOn(Dispatchers.IO)
    }

    fun getContactById(id: Int): Flow<Contact> = contactsDao.getContactById(id)

    fun saveContact(contact: Contact): Flow<String> {
        return flow {
            val response = contactsService.postContacts(contact)
            Log.d(TAG, "Response: $response")
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                emit(response.body()!!)
            } else {
                if (response.code() == 400) {
                    emit(response.errorBody()!!.string())
                } else {
                    emit("Something went wrong, Please try again !")
                }
            }
        }.flowOn(Dispatchers.IO)


    }
}