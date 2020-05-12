package com.learning.contacts.di.module

import androidx.lifecycle.ViewModelProvider
import com.learning.contacts.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}