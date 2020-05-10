package com.learning.contacts.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.learning.contacts.model.Contact

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun getContactsDao(): ContactsDao

    companion object{
        const val DB_NAME = "contacts_database"

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