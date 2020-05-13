package com.learning.contacts.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.learning.contacts.data.database.ContactsDatabase.Companion.CONTACT_DATABASE_VERSION
import com.learning.contacts.data.database.ContactsDatabase.Companion.EXPORT_SCHEME
import com.learning.contacts.model.Contact

@Database(
    entities = [Contact::class],
    version = CONTACT_DATABASE_VERSION,
    exportSchema = EXPORT_SCHEME
)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun getContactsDao(): ContactsDao

    companion object{
        const val DB_NAME = "contacts_database"
        const val CONTACT_DATABASE_VERSION = 1
        const val EXPORT_SCHEME = false

        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase {
            if(INSTANCE != null ){
                return INSTANCE!!
            }

            synchronized(this){
                 INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    DB_NAME
                )
                .addMigrations()
                .build()

                return INSTANCE!!
            }
        }
    }
}