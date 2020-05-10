package com.learning.contacts.di.module

import android.app.Application
import com.learning.contacts.data.database.ContactsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContactsDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = ContactsDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideContactsDao(database: ContactsDatabase) = database.getContactsDao()
}