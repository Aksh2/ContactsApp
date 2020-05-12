package com.learning.contacts.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Abstract activity which binds ViewModel [VM] and ViewBinding [VB]
 */
abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    @Inject
    protected lateinit var mViewModelProvider: ViewModelProvider.Factory

    protected val mViewModel by lazy { getViewModel() }

    protected lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        mViewBinding = getViewBinding()
    }


    /**
     * returns VB which is assigned to mViewBinding in onCreate
     */

    abstract fun getViewBinding() : VB

    /**
     * returns VM which is assigned to mViewModel
     */

    abstract fun getViewModel() : VM
}