package com.learning.contacts.ui.contactdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.learning.contacts.data.repository.ContactRepository
import javax.inject.Inject

class ContactDetailsViewModel @Inject constructor(private val contactsRepository: ContactRepository) :
    ViewModel() {

    fun getContactById(id: Int) = contactsRepository.getContactById(id).asLiveData()
}