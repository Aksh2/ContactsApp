package com.learning.contacts.ui.contactdetails

import android.os.Bundle
import androidx.lifecycle.Observer
import com.learning.contacts.databinding.ActivityContactDetailsBinding
import com.learning.contacts.ui.base.BaseActivity
import com.learning.contacts.utils.viewModelOf

class ContactDetailActivity :
    BaseActivity<ContactDetailsViewModel, ActivityContactDetailsBinding>() {

    companion object {
        const val CONTACT_ID = "contactId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        title = "Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val contactId = intent.extras?.getInt(CONTACT_ID)
            ?: throw IllegalArgumentException("Contact Id must not be null")

        initialiseContactDetails(contactId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun initialiseContactDetails(contactId: Int) {
        mViewModel.getContactById(contactId).observe(this, Observer {
            mViewBinding.tvFirstName.text = it.firstname
            mViewBinding.tvLastName.text = it.lastname
            mViewBinding.tvEmail.text = it.email
            mViewBinding.tvMobile.text = it.phone
        })
    }

    override fun getViewBinding(): ActivityContactDetailsBinding =
        ActivityContactDetailsBinding.inflate(layoutInflater)

    override fun getViewModel(): ContactDetailsViewModel =
        viewModelOf<ContactDetailsViewModel>(mViewModelProvider)
}
