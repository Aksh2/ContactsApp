package com.learning.contacts.di.component

import android.app.Application
import com.learning.contacts.ContactsApp
import com.learning.contacts.ViewModelProviderFactory
import com.learning.contacts.data.database.ContactsDatabase
import com.learning.contacts.di.builder.ActivityBuilder
import com.learning.contacts.di.module.ContactsDatabaseModule
import com.learning.contacts.di.module.NetworkModule
import com.learning.contacts.di.module.ViewModelFactoryModule
import com.learning.contacts.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ContactsDatabaseModule::class,
        ActivityBuilder::class,
        NetworkModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<ContactsApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: ContactsApp)
}