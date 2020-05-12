package com.learning.contacts.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.contacts.data.repository.ContactRepository
import com.learning.contacts.model.Contact
import com.learning.contacts.network.ResponseState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val contactRepository: ContactRepository) :
    ViewModel() {

    private val _contactLiveData = MutableLiveData<ResponseState<List<Contact>>>()

    val postContactLiveData: LiveData<ResponseState<List<Contact>>>
        get() = _contactLiveData

    fun getContacts() {
        viewModelScope.launch {
            contactRepository.getAllContacts().collect {
                _contactLiveData.value = it
            }
        }
    }

}