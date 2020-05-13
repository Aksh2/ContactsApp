package com.learning.contacts.di.builder

import com.learning.contacts.ui.addcontact.AddContactActivity
import com.learning.contacts.ui.contactdetails.ContactDetailActivity
import com.learning.contacts.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindContactDetailActivity(): ContactDetailActivity

    @ContributesAndroidInjector
    abstract fun bindAddContactActivity(): AddContactActivity
}