package com.learning.contacts.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.learning.contacts.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {
       @Insert(onConflict = OnConflictStrategy.REPLACE)
       fun insertContactList(contacts: List<Contact>)

       @Query("SELECT * FROM ${Contact.CONTACTS_TABLE_NAME}")
       fun getAllContacts(): Flow<List<Contact>>

       @Query("SELECT * from ${Contact.CONTACTS_TABLE_NAME} where id=:id")
       fun getContactById(id: Int): Flow<Contact>
}