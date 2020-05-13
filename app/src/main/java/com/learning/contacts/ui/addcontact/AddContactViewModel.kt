package com.learning.contacts.ui.addcontact

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.learning.contacts.data.repository.ContactRepository
import com.learning.contacts.model.Contact
import javax.inject.Inject

class AddContactViewModel @Inject constructor(private val contactsRepository: ContactRepository) :
    ViewModel() {

    fun saveNewContact(contact: Contact): LiveData<String> =
        contactsRepository.saveContact(contact).asLiveData()
}