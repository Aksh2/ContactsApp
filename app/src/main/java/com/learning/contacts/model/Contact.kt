package com.learning.contacts.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.contacts.model.Contact.Companion.CONTACTS_TABLE_NAME

@Entity(tableName = CONTACTS_TABLE_NAME)
data class Contact (

    @PrimaryKey
    var id: Int? = 0,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = ""

){
    companion object{
        const val CONTACTS_TABLE_NAME="contacts"
    }
}